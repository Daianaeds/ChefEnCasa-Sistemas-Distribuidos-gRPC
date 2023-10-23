(function () {
    const usernameActive = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!usernameActive || !password) {
        window.location.replace("/");
    }

    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = usernameActive;

    fetch("/api/user/favouriteUsers/" + usernameActive, {
        Headers: { "Content-Type": "application/json" },
    }).then(function (res) {
        return res.json();
    }).then(data => {

        var listaFavoritos = data;

        fetch("/api/user/users", {
            headers: { "Content-Type": "application/json" },

        }).then(function (res) {
            return res.json();
        }).then(function (obj) {
            const content = document.querySelector('#content-usuarios');
            let html = "<div class='container row'>";

            obj.response.forEach((element) => {

                if (element.username !== usernameActive) {
                    var claseBoton = "Follow"; // boton follow

                    var usuarioEncontrado = listaFavoritos.response.find(function (usuarioFav) {
                        return usuarioFav.username === element.username;
                    });

                    if (usuarioEncontrado) {
                        claseBoton = "Unfollow"; // boton unfollow
                    }

                    html += "<div class='col-mc-1'><div class='card card-body position-relative' style='width: 18rem;' style= 'display:block'>"
                    html += "<p>" + element.username + "</p>"
                    html += "<button id='btn-fav-" + element.username + "' class='btn btn-primary ml-auto' onclick='toggleFavoritosEnServidor(\"" + usernameActive + "\",\"" + element.username + "\")' data-username='${element.username}' >" + claseBoton + "</button>"
                    html += "</div>"
                }
            })
            html += '</div>'
            content.innerHTML = html;
        }).catch(function (e) {
            console.error(e);
        })
    }).catch(function (e) {
        console.error(e);
    })
})();

function toggleFavoritosEnServidor(usernameActive, username) {
    var elemento = document.getElementById("btn-fav-" + username);
    var endpoint;

    if (elemento.textContent.trim() === "Unfollow") {
        elemento.textContent = "Follow";
        endpoint = "/api/user/unfollow-user";
    } else {
        elemento.textContent = "Unfollow";
        endpoint = "/api/user/follow-user";
    }

    // Realiza una solicitud HTTP POST al servidor para guardar los favoritos
    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username: usernameActive, // Reemplaza con el nombre de usuario real
            favouriteUsername: username,
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            // Maneja la respuesta del servidor si es necesario
            console.log(data);
        })
        .catch((error) => {
            console.error('Error al guardar los favoritos:', error);
        });
}