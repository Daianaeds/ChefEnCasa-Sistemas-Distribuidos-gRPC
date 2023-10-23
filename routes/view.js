const express = require('express');
const viewRouter = express.Router();
const path = require('path')

/***************INDEX**********************/
viewRouter.get('/', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/index.html'));
});

viewRouter.get('/styles/index.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/index.css'));
});

viewRouter.get('/Js/index.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/index.js'));
});


/***************HOME**********************/
viewRouter.get('/home', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/home.html'));
});

viewRouter.get('/styles/home.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/home.css'));
});

viewRouter.get('/Js/home.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/home.js'));
});

/***************PUBLICAR-RECETA**********************/

viewRouter.get('/publicarReceta', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/publicarReceta.html'));
});

viewRouter.get('/styles/publicarReceta.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/publicarReceta.css'));
});

viewRouter.get('/Js/publicarReceta.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/publicarReceta.js'));
});

/***************RECETAS-FAVORITAS**********************/

viewRouter.get('/recetasFavoritas', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/recetasFavoritas.html'));
});

viewRouter.get('/styles/recetasFavoritas.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/recetasFavoritas.css'));
});

viewRouter.get('/Js/recetasFavoritas.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/publicarReceta.js'));
});

/***************RECETAS**********************/

viewRouter.get('/recetas', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/recetas.html'));
});

viewRouter.get('/styles/recetas.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/recetas.css'));
});

viewRouter.get('/Js/recetas.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/recetas.js'));
});

/***************USUARIOS**********************/

viewRouter.get('/usuarios', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/usuarios.html'));
});

viewRouter.get('/styles/usuarios.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/usuarios.css'));
});

viewRouter.get('/Js/usuarios.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/usuarios.js'));
});

/***************REGISTRARSE**********************/

viewRouter.get('/register', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/register.html'));
});

viewRouter.get('/styles/register.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/register.css'));
});

viewRouter.get('/Js/register.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/register.js'));
});

/***************RECETA-SOLA**********************/

viewRouter.get('/recetaSola', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/recetaSola.html'));
});

viewRouter.get('/styles/recetaSola.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/recetaSola.css'));
});

viewRouter.get('/Js/recetaSola.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/recetaSola.js'));
});

/***************FILTRO-RECETAS**********************/

viewRouter.get('/filterRecipe', (req, res) => {
    res.sendFile(path.join(__dirname, '..', '/views/filterRecipe.html'));
});

module.exports = viewRouter;

