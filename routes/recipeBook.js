const express = require('express');
const recipeBookRouter = express.Router();
const SoapConfiguration = require('../Soap/client.js')
const soapConfig = new SoapConfiguration()



//Crear recetario
recipeBookRouter.post('/save/recipebooks', (req, res) => {
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
recipeBookRouter.delete('/delete/recipebooks', (req, res) => {
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
recipeBookRouter.post('/add-recipe', (req, res) => {
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
recipeBookRouter.delete('/delete-recipe', (req, res) => {
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

recipeBookRouter.get('/listRecipeBooks/:username', (req, res) => {
    soapConfig.listRecipeBooks(req.params.username, (err, result) => {
        if (err) {
            res.json(err)
        } else {
            res.json(result)
        }
    })
})

//traer todas las recetas de un book
recipeBookRouter.get('/:idRecipeBook', (req, res) => {
    soapConfig.getRecipeBook(req.params.idRecipeBook, (err, result) => {
        if (err) {
            res.json(err)
        } else {
            res.json(result)
        }
    })
})

module.exports = recipeBookRouter;