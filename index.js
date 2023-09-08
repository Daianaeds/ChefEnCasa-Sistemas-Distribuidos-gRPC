const client = require('./client.js')
const express = require('express')
const bodyParser = require('body-parser')
const { log } = require('@grpc/grpc-js/build/src/logging')

const app = express()
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }));

app.get('/', function (req, res) {
  client.hello({}, (error, news) => {
    console.log('entro a bienvenida')
    res.sendFile(__dirname + "/login.html");
    if (!error) console.log(news)
  })
})

app.post('/redireccionar',(req, res) =>{
  const botonPresionado = req.body.botones;

  if (botonPresionado === 'Loguearse') {
    // Redirigir a Vista A
    //res.redirect('/publicarReceta.html');
    res.sendFile(__dirname + "/publicarReceta.html");
  } else if (botonPresionado === 'Registrarse') {
    // Redirigir a Vista B
    //res.redirect('/register.html');
    res.sendFile(__dirname + "/register.html");
  } else {
    // Manejar otros casos o errores
    res.send('Acción no reconocida');
  }

});

app.post('/save', (req, res) => {
  let user = {
    name: req.body.name,
    email: req.body.email,
    user: req.body.user,
    password: req.body.password,
  }

  client.createUser(user, (err, data) => {
    if (!err) console.log('user created successfully', data)
    res.send(JSON.stringify('ok')).status(200)
  })
})

app.listen(5555, () => {
  console.log('Server running at port %d', 5555)
})
