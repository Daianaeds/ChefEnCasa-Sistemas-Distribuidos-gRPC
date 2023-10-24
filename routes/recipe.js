const express = require('express');
const recipeRouter = express.Router();
const client = require('../client.js')
const kafkaConfiguration = require('../Kafka/ConfigKafka.js')
const kafkaConfig = new kafkaConfiguration()

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

//Crear receta y guarda en el topico Novedades las recetas
recipeRouter.post('/save-recipe', (req, res) => {
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
recipeRouter.post('/follow-recipe', (req, res) => {
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
recipeRouter.post('/unfollow-recipe', (req, res) => {
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
recipeRouter.get('/favouriteRecipe/:username', (req, res) => {
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
recipeRouter.get('/recipes', (req, res) => {
    client.listRecipes({}, (err, data) => {
        if (!err) {
            res.json(data)
        } else {
            res.status(400).send('Falló al realizar la busqueda')
        }
    })
})

//Listar las recetas por filtro.
recipeRouter.get('/filter-recipes', (req, res) => {
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
recipeRouter.get('/:idRecipe', (req, res) => {
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


//AgregarComentario
recipeRouter.post('/addComment', (req, res) => {
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
recipeRouter.post('/addStars', (req, res) => {
    try {
        addPopularidadReceta(req.body.idRecipe, req.body.score)
        res.status(200).json({ message: 'Receta puntuada correctamente' })
    } catch (error) {
        res.status(500).json({ error: 'Error al guardar el puntaje de estrellas' })
    }
})




module.exports = recipeRouter;