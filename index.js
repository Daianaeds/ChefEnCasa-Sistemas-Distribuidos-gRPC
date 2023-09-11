const client = require('./client.js')
const express = require('express')
const bodyParser = require('body-parser')
const { log } = require('@grpc/grpc-js/build/src/logging')
const auth = require('./middleware/auth')
const jwt = require('jsonwebtoken')

const cors = require('cors')

var corsOptions = {
  origin: '*',
  optionsSuccessStatus: 200,
}

const app = express()
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors(corsOptions))

app.get('/', function (req, res) {
  res.sendFile(__dirname + "/views/index.html");
})

/*app.post('/redireccionar',(req, res) =>{
  const botonPresionado = req.body.botones;

  if (botonPresionado === 'Loguearse') {
    // Redirigir a Vista A
    //res.redirect('/publicarReceta.html');
    res.sendFile(__dirname + "/views/publicarReceta.html");
  } else if (botonPresionado === 'Registrarse') {
    // Redirigir a Vista B
    //res.redirect('/register.html');
    res.sendFile(__dirname + "/views/register.html");
  } else {
    // Manejar otros casos o errores
    res.send('Acción no reconocida');
  }

});*/

app.post('/publicarReceta', function (req, res) {
  res.sendFile(__dirname + "/views/publicarReceta.html");
})

app.post('/register', function (req, res) {
  res.sendFile(__dirname + "/views/register.html");
})

app.post('/login', (req, res) => {
  // Get user input
  let userAuth = {
    username: req.body.username,
    password: req.body.password,
  }

  // Validar contenido de usuario y pass
  if (!(userAuth.username && userAuth.password)) {
    res.status(400).send('All input is required')
  }

  client.authentication(userAuth, (err, data) => {
    if (data.error == '') {
      const response = { username: userAuth.username, token: '' }
      // Crear token/ Sign toma algunos datos y un secreto o clave privada y crea un JWT firmado que contiene esos datos.
      const TOKEN_KEY = 'RANDOMSTRING'
      const token = jwt.sign({ username: userAuth.username }, TOKEN_KEY, {
        expiresIn: '5h',
      })
      response.token = token
      res.status(201).json(response)
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
    console.log(data)
    console.log(err)
    if (!data.error) {
      console.log("1")
      res.send(JSON.stringify(data)).status(200)
    } else {
      console.log("2")
      res.status(400).send("<h1>Fallo logueo<h1/>")
    }
  })
})

//Listar todos los usuarios.
app.get('/users', auth, (req, res) => {
  client.listUser({}, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
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

//Dejar de seguir user
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

//Usuarios favoritos
app.get('/favouriteUsers/:username', (req, res) => {
  let request = {
    requestOrResponse: req.params.username,
  }
  client.favouriteUsers(request, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Crear receta
app.post('/save-recipe', auth, (req, res) => {
  console.log("pepe", req);
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

//Seguir receta
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
//Dejar de seguir receta
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

//Listar las recetas favoritas
app.get('/favouriteRecipe/:username', (req, res) => {
  let request = {
    requestOrResponse: req.params.username,
  }
  client.favouriteRecipes(request, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Listar recetas
app.get('/recipes', (req, res) => {
  client.listRecipes({}, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Listar las recetas por filtro.
app.get(
  '/filter-recipes/name_ingredient/:name_ingredient?/title/:title?/name_category/:name_category?/time/:time_minutes?',
  (req, res) => {
    let findRecipeRequest = {
      auth: req.params.auth,
      name_ingredient: req.params.name_ingredient,
      title: req.params.title,
      name_category: req.params.name_category,
      time_minutes: req.params.time_minutes,
    }
    client.findRecipeByFilter(findRecipeRequest, (err, data) => {
      if (!err) {
        res.json(data)
      } else {
        res.status(400).send('Falló al realizar la busqueda')
      }
    })
  }
)

app.listen(5555, () => {
  console.log('Client running at port %d', 5555)
})
