function validation(ev, from) {
  const username = window.localStorage.setItem("username", ev.form.username.value);
  const password = window.localStorage.setItem("password", ev.form.password.value);

  if (from == "register") {
    window.location.replace("/register");
  } else {
    fetch("/api/user/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        username: ev.form.username.value,
        password: ev.form.password.value,
      }),
    })
      .then(function (response) {
        if (response.ok) {
          // Convierte la respuesta en un objeto JSON
          return response.json();
        } else {
          document.getElementById("error").innerHTML =
            "El usuario y/o contraseña son incorrectos";
        }
      })
      .then(function (data) {
        if (data.isMod) {
          window.alert(data.isMod);

          window.location.replace("/homemod");
        } else {
          window.alert(data.isMod);
          window.location.replace("/home");
        }
      })
      .catch(function (error) {
        console.error(error);
        document.getElementById("error").textContent = "Rompio";
      });
  }
}
