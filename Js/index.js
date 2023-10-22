function validation(ev, from) {
    if (from == "register") {
      window.location.replace("/register");
    } else {
      fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          username: ev.form.username.value,
          password: ev.form.password.value,
        }),
      })
        .then(function (e) {
          if (e.ok) {
            window.localStorage.setItem("username", ev.form.username.value);
            window.localStorage.setItem("password", ev.form.password.value);
            if (e.isMod == true){
              window.location.replace("/homemod");
            }
            else{
              window.location.replace("/home");
            }
          } else {
            document.getElementById("error").innerHTML =
              "El usuario y/o contraseña son incorrectos";
          }
        })
        .catch(function (e) {
          console.error(e);
          document.getElementById("error").textContent = "Rompio";
        });
    }
  }