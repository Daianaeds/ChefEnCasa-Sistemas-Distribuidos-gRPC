const express = require('express');
const denunciasRouter = express.Router();
const SoapConfiguration = require('../Soap/client.js')
const soapConfig = new SoapConfiguration()


//Agregar denuncias
denunciasRouter.post('/add/denunciation', (req, res) => {
    let args = {
        idRecipe: req.body.idRecipe,
        username: req.body.username,
        motive: req.body.motive,
    }
    soapConfig.addDenunciation(args, (err, result) => {
        if (err) {
            res.json(err)
        } else {
            res.json(result)
        }
    })
})

//eliminar denuncias
denunciasRouter.delete('/delete/denunciation', (req, res) => {
    soapConfig.deleteDenunciation(req.body.idRecipe, (err, result) => {
        if (err) {
            res.json(err)
        } else {
            res.json(result)
        }
    })
})

//ignorar denuncias
denunciasRouter.post('/ignore/denunciation/:idDenunciation', (req, res) => {
    soapConfig.ignoreDenunciation(req.params.idDenunciation, (err, result) => {
        if (err) {
            res.json(err)
        } else {
            res.json(result)
        }
    })
})

//Listar denuncias
denunciasRouter.get('/denunciations', (req, res) => {
    soapConfig.denunciations({}, (err, result) => {
        if (err) {
            res.json(err)
        } else {
            res.json(result)
        }
    })
})


module.exports = denunciasRouter;