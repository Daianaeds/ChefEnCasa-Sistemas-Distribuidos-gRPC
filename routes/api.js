const express = require('express');
const usersRouter = require('./users'); 
const apiRouter = express.Router();

//apiRouter.use("/recetas", recetasRouter);
apiRouter.use("/users", usersRouter);



module.exports = apiRouter;