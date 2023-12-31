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
    res.sendFile(path.join(__dirname, '..', '/Js/recetasFavoritas.js'));
});

/***************RECETARIOS**********************/
viewRouter.get('/recetasRecetario', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/recetasRecetario.html'));
});

viewRouter.get('/styles/recetasRecetario.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/recetasRecetario.css'));
});

viewRouter.get('/Js/recetasRecetario.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/recetasRecetario.js'));
});

/***************RECETAS-RECETARIOS**********************/
viewRouter.get('/recetarios', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/recetarios.html'));
});

viewRouter.get('/styles/recetarios.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/recetarios.css'));
});

viewRouter.get('/Js/recetarios.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/recetarios.js'));
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

/***************METODO-CARGA**********************/

viewRouter.get('/publicarReceta/metodo_carga', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/metodo_carga.html'));
});

viewRouter.get('/styles/metodo_carga.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/metodo_carga.css'));
});

/***************CARGA-MASIVA**********************/

viewRouter.get('/carga_masiva', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/carga_masiva.html'));
});

viewRouter.get('/styles/carga_masiva.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/carga_masiva.css'));
});

viewRouter.get('/Js/carga_masiva.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/carga_masiva.js'));
});

/***************RESUMEN-RECETAS**********************/

viewRouter.get('/resumen_recetas', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/resumen_recetas.html'));
});

viewRouter.get('/styles/resumen_recetas.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/resumen_recetas.css'));
});

viewRouter.get('/Js/resumen_recetas.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/resumen_recetas.js'));
});

/***************HOME-MOD**********************/

viewRouter.get('/homemod', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/viewsMod/homemod.html'));
});

viewRouter.get('/stylesMod/homemod.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/stylesMod/homemod.css'));
});

viewRouter.get('/JsMod/homemod.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/JsMod/homemod.js'));
});

/***************DENUNCIAS-MOD**********************/

viewRouter.get('/denuncia', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/viewsMod/denuncia.html'));
});

viewRouter.get('/stylesMod/denuncia.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/stylesMod/denuncia.css'));
});

viewRouter.get('/JsMod/denuncia.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/JsMod/denuncia.js'));
});

/***************FILTRO-RECETAS**********************/

viewRouter.get('/filterRecipe', (req, res) => {
    res.sendFile(path.join(__dirname, '..', '/views/filterRecipe.html'));
});

/***************MENSAJERIA - Enviar mensaje**********************/

viewRouter.get('/enviarMens', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/enviarMens.html'));
});

viewRouter.get('/styles/enviarMens.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/enviarMens.css'));
});

viewRouter.get('/Js/enviarMens.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/enviarMens.js'));
});

/***************MENSAJERIA - Enviados**********************/

viewRouter.get('/enviados', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/mensEnviados.html'));
});

viewRouter.get('/styles/mensEnviados.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/mensEnviados.css'));
});

viewRouter.get('/Js/mensEnviados.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/mensEnviados.js'));
});


/***************MENSAJERIA - Recibidos**********************/

viewRouter.get('/recibidos', function (req, res) {
    res.sendFile(path.join(__dirname, '..', '/views/mensRecibidos.html'));
});

viewRouter.get('/styles/mensRecibidos.css', function (req, res) {
    res.setHeader('Content-Type', 'text/css');
    res.sendFile(path.join(__dirname, '..', '/styles/mensRecibidos.css'));
});

viewRouter.get('/Js/mensRecibidos.js', function (req, res) {
    res.setHeader('Content-Type', 'application/javascript');
    res.sendFile(path.join(__dirname, '..', '/Js/mensRecibidos.js'));
});

module.exports = viewRouter;


