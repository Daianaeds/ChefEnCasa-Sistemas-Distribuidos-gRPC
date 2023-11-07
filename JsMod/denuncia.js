

(function () {
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!username || !password) {
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;
    const recetaID = window.localStorage.getItem("idReceta");
    const complainingUser = window.localStorage.getItem("complainingUser");
    const complainingMotive = window.localStorage.getItem("complainingMotive");
    const complainingID = window.localStorage.getItem("complainingID");

    fetch("/api/recipe/" + recetaID, {
        method: "GET",
        headers: { "Content-Type": "application/json" },

    }).then(function (res) {
        return res.json();
    }).then(function (obj) {
        const content = document.querySelector('#content-receta');
        let html = "<div class='col-md-12 max-width: 800px'><div class='card card-body position-relative'>"
        html += "<div class='row g-0'>"
        html += "<div class='col-md-4'>"
        html += "<img src='" + obj.pictures[0].url + "' alt='img-i' class='img-fluid rounded-start'/>"

        html += "</div>"
        html += "<div class='col-md-8'>"
        html += "<div class = 'card-body'>"
        html += "<h3 class = 'card-title'>" + obj.title + "</h3>"
        html += "<p><strong>" + "Creada por: </strong>" + obj.username + "</p>"
        html += "<p class='card-text'><strong>Promedio entre recetas: </strong>" + obj.score + "</p>"
        html += "<p class = 'card-text'><strong>" + "Categoría: " + "</strong>" + obj.name_category + "</p>"
        html += "<p class = 'card-text'><strong>" + "Descripción: " + "</strong>" + obj.description + "</p>"
        html += "<p class = 'card-text'><strong>" + "Pasos: " + "</strong>" + obj.steps + "</p>"
        html += "<p class = 'card-text'><strong>" + "Tiempo de cocción: " + "</strong>" + obj.time_minutes + " minutos" + "</p>"
        html += "<p class = 'card-text'><strong>" + "Ingredientes: " + "</strong></p>"
        html += "<ul>";
        obj.ingredients.forEach((ingredient) => {
            html += "<li>" + ingredient.nombre + " " + ingredient.cantidad + "</li>";
        });
        html += "</ul>";

        html += "</div></div>";
        html += "<h5>Comentarios de los usuarios sobre la receta:</h5>"
        html += "<div class= 'card-body' >"
        obj.comments.forEach((comment) => {
            html += "<p><strong>" + comment.username + ": </strong> " + comment.comment + "</p>"
        })
        html += "<div class='col-md-12'>"
        html += "<h5>Detalles de la denuncia:</h5>"
        html += "<p><strong>Usuario denunciante: </strong>\"" + complainingUser + "\"</p>"
        html += "<p><strong>Motivo de la denuncia: </strong>\"" + complainingMotive + "\"</p>"
        html += "<div><button type='button' class='btn btn-success' onclick='ignorarDenuncia(" + complainingID + ")'> Ignorar denuncia</button>"
        html += "<button type='button' class='btn btn-danger' onclick='eliminarReceta()'> Eliminar receta</button></div></div>"
        html += "</div>"

        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();



function ignorarDenuncia(idDenuncia) {

    fetch("/api/denuncias/ignore/denunciation/" + idDenuncia, {
        method: "POST",
        headers: { "Content-Type": "application/json" },

    }).then((response) => response.json()
    ).then(function (obj) {
        window.alert("Receta ignorada!");
        console.log(obj);
        window.location.replace("/homemod");
    }).catch(function (e) {
        console.error('Error al ignorar denuncia: ', e);
    })


}

function eliminarReceta() {
    const idReceta = window.localStorage.getItem("idReceta");
    console.log("receta: " + idReceta)
    /*const textarea = document.querySelector('#box-comentarios');
    const comentario = textarea.value;*/

    fetch("/api/denuncias/delete/denunciation/" + idReceta, {
        method: "POST",
        headers: { "Content-Type": "application/json" },

    }).then((response) => response.json()
    ).then(function (obj) {
        // Maneja la respuesta del servidor si es necesario
        console.log(obj);
        window.location.replace("/homemod");
    }).catch(function (e) {
        console.error('Error al eliminar receta: ', e);
    })
}