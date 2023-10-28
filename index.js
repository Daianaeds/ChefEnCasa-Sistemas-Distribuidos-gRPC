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
const multer = require('multer')
const FormData = require('form-data')
const fs = require('fs')
const serverUrl = 'http://localhost:8080/api/'

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

app.get('/get-data', (req, res) => {
  axios
    .get('http://127.0.0.1:8080/api/prueba')
    .then((response) => {
      console.log('Datos recibidos:', response.data)
    })
    .catch((error) => {
      console.error('Error al realizar la solicitud GET:', error)
    })
})

const upload = multer()

app.post('/uploadFile/:username', upload.single('borrador'), (req, res) => {
  const borrador = req.file
  let username = req.params.username

  if (!borrador) {
    return res.status(400).json({ error: 'No se proporcionó un archivo CSV válido' })
  }
  const formData = new FormData()
  formData.append('borrador', borrador.buffer, { filename: 'borrador.csv' })
  axios
    .post('http://127.0.0.1:8080/api/uploadFile/' + username, formData, {
      headers: {
        ...formData.getHeaders(),
      },
    })
    .then((response) => {
      console.log('Archivo CSV enviado al servidor')
      console.log('Respuesta del servidor Java:', response.data)
      res.json(response.data)
    })
    .catch((error) => {
      console.error('Error al enviar el archivo CSV al servidor Java:', error)
      res.status(500).json({ error: 'Error al enviar el archivo CSV al servidor Java' })
    })
})

app.listen(5555, () => {
  console.log('Client running at port %d', 5555)
})
