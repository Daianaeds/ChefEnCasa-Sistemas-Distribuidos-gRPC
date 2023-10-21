function registrarse(ev, from) {
    fetch("/api/save-user", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        name: ev.form.name.value,
        email: ev.form.email.value,
        username: ev.form.username.value,
        password: ev.form.password.value,
      }),
    })
      .then(function (e) {
        if (e.ok) {
          window.alert("Registro exitoso!");
          window.location.replace("/");
        } else {
          document.getElementById("error").innerHTML =
            "Error al registrarse";
        }
      })
      .catch(function (e) {
        console.error(e);
        document.getElementById("error").textContent = "Rompio";
      });
  }