const apiRouter = require('./routes/api.js')
const viewRouter = require('./routes/view.js')
const client = require('./client.js')
const express = require('express')
const bodyParser = require('body-parser')
const { log } = require('@grpc/grpc-js/build/src/logging')
const auth = require('./middleware/auth')
const jwt = require('jsonwebtoken')
const path = require('path')
const cors = require('cors')
const kafkaConfiguration = require('./Kafka/ConfigKafka.js')
const SoapConfiguration = require('./Soap/client.js')
// Se instancia kafka.
const kafkaConfig = new kafkaConfiguration()
const app = express()
const axios = require('axios')

//Libererias para el manejo archivo csv
const multer = require('multer')
const FormData = require('form-data')
const fs = require('fs')

const serverRestUrl = 'http://127.0.0.1:8080'

//Se instancia Soap.
const swaggerUi = require('swagger-ui-express')
const swaggerJSDoc = require('swagger-jsdoc')
const soapConfig = new SoapConfiguration()
var corsOptions = {
  origin: '*',
  optionsSuccessStatus: 200,
}

//Configuraciones Swagger
const swaggerOptions = {
  swaggerDefinition: {
    openapi: '3.0.0',
    info: {
      title: 'Mi API sistemas-distribuidos',
      version: '1.0.0',
    },
  },
  apis: ['./swagger/swagger.yaml'],
}

const swaggerSpec = swaggerJSDoc(swaggerOptions)
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec))

//Configuraciones para routeo
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))
app.use(cors(corsOptions))
app.use('/public', express.static(path.join(__dirname, 'public')))

app.use('/api', apiRouter)
app.use('/', viewRouter)

//Consumir los ultimos 5 mensajes de novedades
app.get('/lastFiveMessageNovedades', (req, res) => {
  const messages = kafkaConfig.lastMessageNovedades()
  res.json(messages)
})

// Crea una instancia de Middleware Multer para manejar la carga de archivos en una solicitud HTTP.
const upload = multer()

//Se guarda el contenido del borrador
app.post('/api/uploadFile/:username', upload.single('borrador'), (req, res) => {
  const borrador = req.file
  let username = req.params.username

  //Pregunta si esta vacio.
  if (!borrador) {
    return res.status(400).json({ error: 'No se proporcionó un archivo CSV válido' })
  }
  // Crea un objeto FormData para enviar el archivo CSV en el header
  const formData = new FormData()
  formData.append('borrador', borrador.buffer, { filename: 'borrador.csv' })
  axios
    .post(serverRestUrl + '/api/uploadFile/' + username, formData, {
      headers: {
        ...formData.getHeaders(),
      },
    })
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//Obtener el lista de recetas incompletas por usuario
app.get('/api/incompleteRecipes/:username', (req, res) => {
  if (req.params.username == null) {
    res.json('Ingrese el usuario')
  }
  axios
    .get(serverRestUrl + '/api/incompleteRecipes/' + req.params.username)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//traer una sola receta incompleta
app.get('/api/incompleteRecipe/:idIncompleteRecipe', (req, res) => {
  axios
    .get(serverRestUrl + '/api/incompleteRecipe/' + req.params.idIncompleteRecipe)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//Eliminar una receta incompleta
app.delete('/api/delete/incompleteRecipe/:idIncompleteRecipe', (req, res) => {
  axios
    .delete(serverRestUrl + '/api/delete/incompleteRecipe/' + req.params.idIncompleteRecipe)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//Guadar receta -> Lo mismo que guardar pero se suma el id de la receta incompleta
app.post('/api/saveRecipe', (req, res) => {
  let recipe = {
    auth: req.body.auth,
    title: req.body.title,
    description: req.body.description,
    steps: req.body.steps,
    time_minutes: req.body.time_minutes,
    name_category: req.body.name_category,
    ingredients: req.body.ingredients,
    pictures: req.body.pictures,
    idIncompleteRecipe: req.body.idIncompleteRecipe,
  }
  axios
    .post(serverRestUrl + '/api/saveRecipe', recipe)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//APIS MAILS

//Crear mail
app.post('/api/InternalMail/create', (req, res) => {
  let mail = {
    source: req.body.source,
    subject: req.body.subject,
    destination: req.body.destination,
    subjectReply: req.body.subjectReply,
  }
  axios
    .post(serverRestUrl + '/InternalMail/create', mail)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//trae los mail recibidos
app.get('/api/inbox/:destination', (req, res) => {
  axios
    .get(serverRestUrl + '/InternalMail/inbox/' + req.params.destination)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//trae los mail enviados
app.get('/api/InternalMail/sent/:source', (req, res) => {
  axios
    .get(serverRestUrl + '/InternalMail/sent/' + req.params.source)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//trae un mail por el id recibido
app.get('/api/InternalMail/:mail_id', (req, res) => {
  axios
    .get(serverRestUrl + '/InternalMail/' + req.params.mail_id)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

//Responder
app.post('/api/InternalMail/reply', (req, res) => {
  let response = {
    id: req.body.id,
    message: req.body.message,
  }
  axios
    .post(serverRestUrl + '/InternalMail/reply', response)
    .then((response) => {
      res.json(response.data)
    })
    .catch((error) => {
      res.status(400).json(error.response.data)
    })
})

app.listen(5555, () => {
  console.log('Client running at port %d', 5555)
})
