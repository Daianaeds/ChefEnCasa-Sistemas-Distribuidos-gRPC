const client = require('../client.js')
const express = require('express');
const usersRouter = express.Router();
const jwt = require('jsonwebtoken');

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
            res.status(201).json(response)
        } else {
            res.status(400).json(data)
        }
    })
})

module.exports = usersRouter;