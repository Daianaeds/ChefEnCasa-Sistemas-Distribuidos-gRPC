const indexRouter = require('./routes/indexRoutes.js');
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
const app = express()
// Se instancia kafka.
const kafkaConfig = new kafkaConfiguration()
const soapConfig = new SoapConfiguration()
var corsOptions = {
  origin: '*',
  optionsSuccessStatus: 200,
}

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))
app.use(cors(corsOptions))
app.use('/public', express.static(path.join(__dirname, 'public')))

app.use(indexRouter);

/********************************************************************************************************************************************************************/
//INICIO - ENDPOINTS PARA FRONT
/*************************************/

app.get('/', function (req, res) {
  res.sendFile(__dirname + '/views/home.html')
})

app.get('/styles/home.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/home.css');
});

app.get('/Js/home.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/home.js');
});

/*************************************/

app.get('/publicarReceta', function (req, res) {
  res.sendFile(__dirname + '/views/publicarReceta.html')
})

app.get('/styles/publicarReceta.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/publicarReceta.css');
});

app.get('/Js/publicarReceta.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/publicarReceta.js');
});

/*************************************/

app.get('/recetasFavoritas', function (req, res) {
  res.sendFile(__dirname + '/views/recetasFavoritas.html')
})

app.get('/styles/recetasFavoritas.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/recetasFavoritas.css');
});

app.get('/Js/recetasFavoritas.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/recetasFavoritas.js');
});

/*************************************/

app.get('/recetas', function (req, res) {
  res.sendFile(__dirname + '/views/recetas.html')
})

app.get('/styles/recetas.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/recetas.css');
});

app.get('/Js/recetas.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/recetas.js');
});

/*************************************/

app.get('/usuarios', function (req, res) {
  res.sendFile(__dirname + '/views/usuarios.html')
})

app.get('/styles/usuarios.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/usuarios.css');
});

app.get('/Js/usuarios.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/usuarios.js');
});

/*************************************/

app.get('/register', function (req, res) {
  res.sendFile(__dirname + '/views/register.html')
})

app.get('/styles/register.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/register.css');
});

app.get('/Js/register.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/register.js');
});

/*************************************/

app.get('/home', function (req, res) {
  res.sendFile(__dirname + '/views/home.html');
});

app.get('/styles/home.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/home.css');
});

app.get('/Js/home.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/home.js');
});


/*************************************/

app.get('/recetaSola', function (req, res) {
  res.sendFile(__dirname + '/views/recetaSola.html')
})

app.get('/styles/recetaSola.css', function (req, res) {
  res.setHeader('Content-Type', 'text/css');
  res.sendFile(__dirname + '/styles/recetaSola.css');
});

app.get('/Js/recetaSola.js', function (req, res) {
  res.setHeader('Content-Type', 'application/javascript');
  res.sendFile(__dirname + '/Js/recetaSola.js');
});

/*************************************/

app.get('/filterRecipe', (req, res) => {
  res.sendFile(__dirname + '/views/filterRecipe.html')
})

//FIN - ENDPOINTS PARA FRONT

//INICIO METODOS LLAMADAS GRPC


//Crear y modificar usuario.
app.post('/api/save-user', (req, res) => {
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
      console.log('1')
      res.send(JSON.stringify(data)).status(200)
    } else {
      console.log('2')
      res.status(400).send('<h1>Fallo logueo<h1/>')
    }
  })
})

//Listar todos los usuarios.

app.get('/users', (req, res) => {
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
      addPopularidadUsuario(req.body.favouriteUsername, 1)
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
      addPopularidadUsuario(req.body.favouriteUsername, -1)

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

//Crear receta y guarda en el topico Novedades las recetas
app.post('/api/save-recipe', (req, res) => {
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
    kafkaConfig.consumeNovedades('novedades')
    if (!err) {
      const mensajeNovedades = {
        username: req.body.auth.username,
        title: req.body.title,
        url: req.body.pictures[0],
      }
      const kafkaMsjNovedades = {
        key: 'key1',
        value: new Buffer.from(JSON.stringify(mensajeNovedades)),
      }
      kafkaConfig.produce('novedades', kafkaMsjNovedades)

      res.send(JSON.stringify(data)).status(200)
    } else {
      res.status(400).json(data)
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
      addPopularidadReceta(req.body.idRecipe, 1)
      addPopularidadUsuario(req.body.author, 1) //tiene que capturar el author de la receta
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
      addPopularidadReceta(req.body.idRecipe, -1)
      addPopularidadUsuario(req.body.author, -1) //tiene que capturar el author de la receta
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
app.get('/api/recipes', (req, res) => {
  client.listRecipes({}, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Listar las recetas por filtro.
app.get('/filter-recipes', (req, res) => {
  let findRecipeRequest = {
    // auth: req.params.auth,
    name_ingredient: req.query.name_ingredient,
    title: req.query.title,
    name_category: req.query.name_category,
    time_minutes: req.query.time_minutes,
  }
  client.findRecipeByFilter(findRecipeRequest, (err, data) => {
    if (!err) {
      console.log(data)
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//Buscar receta por id
app.get('/api/recipe/:idRecipe', (req, res) => {
  let recipe = {
    idRecipe: req.params.idRecipe,
  }
  client.findRecipeById(recipe, (err, data) => {
    if (!err) {
      res.json(data)
    } else {
      res.status(400).send('Falló al realizar la busqueda')
    }
  })
})

//FIN METODOS LLAMADAS GRPC

//Consumir los ultimos 5 mensajes de novedades
app.get('/lastFiveMessageNovedades', (req, res) => {
  const messages = kafkaConfig.lastMessageNovedades()
  res.json(messages)
})

//AgregarComentario
app.post('/addComment', (req, res) => {
  const messageComentarios = {
    username: req.body.username,
    idRecipe: req.body.idRecipe,
    comment: req.body.comment,
  }
  const kafkaMsjComentarios = {
    key: 'key1',
    value: new Buffer.from(JSON.stringify(messageComentarios)),
  }
  kafkaConfig.produce('comentarios', kafkaMsjComentarios)
  addPopularidadUsuario(req.body.author, 1) //Dai me puede pasar el usuario creador
  res.json('OK')
})

//Agregar Puntaje Estrellas
app.post('/addStars', (req, res) => {
  try {
    addPopularidadReceta(req.body.idRecipe, req.body.score)
    res.status(200).json({ message: 'Receta puntuada correctamente' });
  } catch (error) {
    res.status(500).json({ error: 'Error al guardar el puntaje de estrellas' });
  }
})

function addPopularidadReceta(idRecipe, score) {
  const messagePopularidadReceta = {
    identifier: idRecipe,
    score: score,
  }
  const kafkaMsjPopularidadReceta = {
    key: 'key1',
    value: new Buffer.from(JSON.stringify(messagePopularidadReceta)),
  }
  kafkaConfig.produce('popularidadReceta', kafkaMsjPopularidadReceta)
}

function addPopularidadUsuario(favouriteUsername, score) {
  const messagePopularidadUsuario = {
    identifier: favouriteUsername,
    score: score,
  }
  const kafkaMsjPopularidad = {
    key: 'key1',
    value: new Buffer.from(JSON.stringify(messagePopularidadUsuario)),
  }
  kafkaConfig.produce('popularidadUsuario', kafkaMsjPopularidad)
}

//Crear recetario
app.post('/save/recipebooks', (req, res) => {
  let args = {
    name: req.body.name,
    username: req.body.username,
  }
  soapConfig.createRecipeBook(args, (err, result) => {
    if (err) {
      res.json(err)
    } else {
      res.json(result)
    }
  })
})

//Eliminar recetario
app.delete('/delete/recipebooks', (req, res) => {
  var idRecipeBook = req.body.idRecipeBook
  soapConfig.deleteRecipeBook(idRecipeBook, (err, result) => {
    if (err) {
      res.json(err)
    } else {
      res.json(result)
    }
  })
})

//agregar receta de recetario
app.post('/add-recipe', (req, res) => {
  var args = {
    idRecipeBook: req.body.idRecipeBook,
    idRecipe: req.body.idRecipe,
  }

  soapConfig.addRecipeToRecipeBook(args, (err, result) => {
    if (err) {
      res.json(err)
    } else {
      res.json(result)
    }
  })
})

//Eliminar receta de recetario
app.delete('/delete-recipe', (req, res) => {
  var args = {
    idRecipeBook: req.body.idRecipeBook,
    idRecipe: req.body.idRecipe,
  }

  soapConfig.deleteRecipeToRecipeBook(args, (err, result) => {
    if (err) {
      res.json(err)
    } else {
      res.json(result)
    }
  })
})

app.get('/listRecipeBooks', (req, res) => {
  soapConfig.listRecipeBooks(req.body.username, (err, result) => {
    if (err) {
      res.json(err)
    } else {
      res.json(result)
    }
  })
})

app.get('/recipebook', (req, res) => {
  soapConfig.getRecipeBook(req.body.idRecipeBook, (err, result) => {
    if (err) {
      res.json(err)
    } else {
      res.json(result)
    }
  })
})

app.listen(5555, () => {
  console.log('Client running at port %d', 5555)
})
