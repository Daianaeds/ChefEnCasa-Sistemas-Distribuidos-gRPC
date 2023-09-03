const client = require('./client.js')
const express = require('express')
const bodyParser = require('body-parser')
const { log } = require('@grpc/grpc-js/build/src/logging')
const auth = require('./middleware/auth')
const jwt = require('jsonwebtoken')

const app = express()
app.use(bodyParser.json())

app.get('/', auth, function (req, res) {
  client.hello({}, (error, news) => {
    console.log('entro')
    if (!error) console.log(news)
  })
})

// Login
//Si esta regitrado devuelve {"name":"","username":"","email":"","error":""}
//Si no esta regitrado devuelve {"name":"","username":"","email":"","error":"Los datos ingresados no son correctos. Intente nuevamente"}
app.post('/login', (req, res) => {
  // Get user input
  let userAuth = {
    username: req.body.username,
    password: req.body.password,
  }

  // Validate user input
  if (!(userAuth.username && userAuth.password)) {
    res.status(400).send('All input is required')
  }

  client.authentication(userAuth, (err, data) => {
    if (!err) {
      res.send(JSON.stringify(data)).status(200)
    } else {
      res.status(400).send(data)
    }
  })

  const response = { username: userAuth.username, token: '' }
  // Create token
  const TOKEN_KEY = 'RANDOMSTRING'
  const token = jwt.sign({ username: userAuth.username }, TOKEN_KEY, {
    expiresIn: '5h',
  })
  response.token = token
  res.status(201).json(response)
})

//Crear y modificar usuario.
app.post('/save-user', (req, res) => {
  let user = {
    name: req.body.name,
    email: req.body.email,
    username: req.body.username,
    password: req.body.password,
  }

  client.newUser(user, (err, data) => {
    if (!err) {
      res.send(JSON.stringify(data)).status(200)
    } else {
      res.status(400).send(data)
    }
  })
})

//Listar todos los usuarios.
//devuelve un username y un email
app.get('/users', auth, (req, res) => {
  client.listUser({}, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Crea receta
app.post('/save-recipe', auth, (req, res) => {
  let recipe = {
    //  userAuth: req.body.userAuth,
    title: req.body.title,
    description: req.body.description,
    steps: req.body.steps,
    time_minutes: req.body.time_minutes,
    name_category: req.body.name_category,
    // ingredients: req.body.ingredients,
    // pictures: req.body.pictures,
  }

  client.newRecipe(recipe, (err, data) => {
    if (!err) {
      res.send(JSON.stringify(data)).status(200)
    } else {
      res.status(400).send(data)
    }
  })
})

app.get('/follow-user', (req, res) => {
  let username = req.body.username
  client.followUser(username, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

app.listen(5555, () => {
  console.log('Server running at port %d', 5555)
})
