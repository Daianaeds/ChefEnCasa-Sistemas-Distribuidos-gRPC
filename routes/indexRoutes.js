const express = require('express');
const jwt = require('jsonwebtoken');
const indexRouter = express.Router();

indexRouter.get('/', function (req, res) {
    res.sendFile(__dirname + '/../views/index.html')
})

indexRouter.get('/styles/index.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(__dirname + '/../styles/index.css');
});

indexRouter.get('/Js/index.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(__dirname + '/../Js/index.js');
});

indexRouter.post('/api/login', (req, res) => {
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
            res.status(201).json(response)
        } else {
            res.status(400).json(data)
        }
    })
})

module.exports = indexRouter;