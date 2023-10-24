const client = require('../client.js')
const express = require('express');
const usersRouter = express.Router();
const jwt = require('jsonwebtoken');
const kafkaConfiguration = require('../Kafka/ConfigKafka.js')
const kafkaConfig = new kafkaConfiguration()

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

usersRouter.post('/login', (req, res) => {
    // Get user input
    let userAuth = {
        username: req.body.username,
        password: req.body.password,
    }
    console.log(userAuth)
    // Validar contenido de usuario y pass
    if (!(userAuth.username && userAuth.password)) {
        res.status(400).send('All input is required')
    }

    client.authentication(userAuth, (err, data) => {
        if (data?.error == '') {
            const response = { username: userAuth.username, token: '' }
            // Crear token/ Sign toma algunos datos y un secreto o clave privada y crea un JWT firmado que contiene esos datos.
            const TOKEN_KEY = 'RANDOMSTRING'
            const token = jwt.sign({ username: userAuth.username }, TOKEN_KEY, {
                expiresIn: '5h',
            })
            response.token = token
            res.json(data)
        } else {
            res.status(400).json(data)
        }
    })
})

//Crear y modificar usuario.
usersRouter.post('/save-user', (req, res) => {
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

usersRouter.get('/users', (req, res) => {
    client.listUser({}, (err, data) => {
        if (!err) {
            res.json(data)
        } else {
            res.status(400).send('Falló al realizar la busqueda')
        }
    })
})

//seguir user
usersRouter.post('/follow-user', (req, res) => {
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
usersRouter.post('/unfollow-user', (req, res) => {
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
usersRouter.get('/favouriteUsers/:username', (req, res) => {
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

module.exports = usersRouter;