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
      const response = { username: userAuth.username, token: '' }
      // Create token
      const TOKEN_KEY = 'RANDOMSTRING'
      const token = jwt.sign({ username: userAuth.username }, TOKEN_KEY, {
        expiresIn: '5h',
      })
      response.token = token
      res.status(201).json(response)
      // res.send(JSON.stringify(data)).status(200)
    } else {
      res.status(400).send(data)
    }
  })
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

//Crear receta

app.post('/save-recipe', auth, (req, res) => {
  let recipe = {
    auth: req.body.auth,
    title: req.body.title,
    description: req.body.description,
    steps: req.body.steps,
    time_minutes: req.body.time_minutes,
    name_category: req.body.name_category,
    ingredients: req.body.ingredients,
    pictures: req.body.pictures,
  }

  client.newRecipe(recipe, (err, data) => {
    if (!err) {
      res.send(JSON.stringify(data)).status(200)
    } else {
      res.status(400).send(data)
    }
  })
})

//seguir user
app.post('/follow-user', (req, res) => {
  let UserAndFavourite = {
    username: req.body.username,
    favouriteUsername: req.body.favouriteUsername,
  }
  client.followUser(UserAndFavourite, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//dejar de seguir user
app.post('/unfollow-user', (req, res) => {
  let UserAndFavourite = {
    username: req.body.username,
    favouriteUsername: req.body.favouriteUsername,
  }
  client.unfollowUser(UserAndFavourite, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Usuarios favotiros
app.get('/favouriteUsers', (req, res) => {
  let request = {
    requestOrResponse: req.body.username,
  }
  client.favouriteUsers(request, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//seguir receta
app.post('/follow-recipe', (req, res) => {
  let UserAndFavouriteRecipe = {
    username: req.body.username,
    idRecipe: req.body.idRecipe,
  }
  client.followRecipe(UserAndFavouriteRecipe, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})
//dejar de seguir receta
app.post('/unfollow-recipe', (req, res) => {
  let UserAndFavouriteRecipe = {
    username: req.body.username,
    idRecipe: req.body.idRecipe,
  }
  client.unfollowRecipe(UserAndFavouriteRecipe, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

app.get('/favouriteRecipe', (req, res) => {
  let request = {
    requestOrResponse: req.body.username,
  }
  client.favouriteRecipes(request, (err, data) => {
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
