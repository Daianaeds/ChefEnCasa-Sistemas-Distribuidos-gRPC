(function () {
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!username || !password) {
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;

    fetch("/api/denuncias/denunciations", {
        method: "GET",
        headers: { "Content-Type": "application/json" },
    })
        .then(function (res) {
            return res.json();
        })
        .then(function (obj) {
            const content = document.querySelector('#content-denuncias');
            let html = "<div class='container row'>";
            obj.denunciationDtoList.forEach((denuncia) => {
                let denunciaMotive = '';
                if (denuncia.motive == 1) {
                    denunciaMotive = 'Contenido inapropiado'
                } else if (denuncia.motive == 2) {
                    denunciaMotive = 'Ingredientes prohibidos'
                } else {
                    denunciaMotive = 'Peligroso para la salud'
                }
                html += "<div class='col-md-4'><div class='card card-body position-relative'>";
                html += "<ul>";
                html += "<li>" + "● " + denuncia.username + " " + "\"" + denunciaMotive + "\"";
                html += "<a href = '/denuncia' role='button' onclick='guardarIdReceta(" + denuncia.idRecipe + ")'>Ver Detalles..</a>";
                html += "</li>";
                guardarDatosDenuncia(denuncia.username, denunciaMotive);
                html += "</ul>";
                html += "</div></div>";
            });
            html += '</div>';
            content.innerHTML = html;
        })
        .catch(function (e) {
            console.error(e);
        });
})();

function guardarDatosDenuncia(usuario, denunciaMotive) {
    window.localStorage.setItem("complainingUser", usuario);
    window.localStorage.setItem("complainingMotive", denunciaMotive);
}

function guardarIdReceta(id) {
    window.localStorage.setItem("idReceta", id);
}
