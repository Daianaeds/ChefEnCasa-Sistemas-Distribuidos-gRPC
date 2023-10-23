const express = require('express');
const userRouter = require('./user');
const recetasRouter = require('./recipe');
const recipeBookRouter = require('./recipeBook');
const denunciasRouter = require('./denuncias')
const apiRouter = express.Router();

apiRouter.use("/recipe", recetasRouter);
apiRouter.use("/user", userRouter);
apiRouter.use("/recipebook", recipeBookRouter);
apiRouter.use("/denuncias", denunciasRouter);


module.exports = apiRouter;