const apiRouter = require('./routes/api.js');
const viewRouter = require('./routes/view.js');
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

app.use("/api", apiRouter);
app.use("/", viewRouter);

//Consumir los ultimos 5 mensajes de novedades
app.get('/lastFiveMessageNovedades', (req, res) => {
  const messages = kafkaConfig.lastMessageNovedades()
  res.json(messages)
})

app.listen(5555, () => {
  console.log('Client running at port %d', 5555)
})