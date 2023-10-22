

(function () {
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!username || !password) {
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;
    const recetaID = window.localStorage.getItem("idReceta");

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

        html += "<label for='puntaje'>Calificar receta: </label>"
        html += "<select id='puntaje' name='puntaje'>"
        html += "<option value='1'>⭐</option>"
        html += "<option value='2'>⭐⭐</option>"
        html += "<option value='3'>⭐⭐⭐</option>"
        html += "<option value='4'>⭐⭐⭐⭐</option>"
        html += "<option value='5'>⭐⭐⭐⭐⭐</option></select>"
        html += "<button class='btn btn-success btn-sm' type='submit' onclick='enviarPuntaje(" + obj.id + ")'>Enviar</button>"
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
            html += "<li>" + "● " + ingredient.nombre + " " + ingredient.cantidad + "</li>";
        });
        html += "</ul>";
        html += "<label><strong>Denunciar receta por: </strong></label>"
        html += "<select id='denuncia' name='denuncia'>"
        html += "<option value='1'>Contenido inapropiado</option>"
        html += "<option value='2'>Ingredientes prohibidos</option>"
        html += "<option value='3'>Peligroso para la salud</option></select>"
        html += "<button class='btn btn-danger btn-sm' type='submit' onclick='enviarDenuncia(" + obj.id + ")'>Denunciar</button>"
        html += "</div></div>"


        html += "<p class='card-text'><strong>Agregar comentarios:</strong></p>";
        html += "<div class='comment-container'>";
        html += "<textarea id='box-comentarios' class='col-md-6' rows='5' placeholder='Ingrese comentario..'></textarea>";
        html += "<button class='btn btn-primary btn-custom' type='submit' onclick='enviarComentarios(\"" + username + "\"," + obj.id + ")' >Enviar</button>";
        html += "</div id='comentarios'>";
        html += "<h5>Comentarios de los usuarios sobre la receta:</h5>"
        html += "<div class= 'card-body' >"
        obj.comments.forEach((comment) => {
            html += "<p><strong>" + comment.username + ": </strong> " + comment.comment + "</p>"
        })
        html += "</div></div></div>";

        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();

function enviarPuntaje(idRecipe) {
    const select = document.querySelector('#puntaje');
    const score = select.value;

    fetch("/addStars", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ // Reemplaza con el nombre de usuario real
            idRecipe: idRecipe,
            score: score,
        }),

    }).then((response) => response.json()
    ).then(function (obj) {
        // Maneja la respuesta del servidor si es necesario
        console.log(obj);
    }).catch(function (e) {
        console.error('Error al ingresar la puntuación: ', e);
    })


}

function enviarDenuncia(idRecipe) {
    const select = document.querySelector('#puntaje');
    const score = select.value;

    fetch("/addStars", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ // Reemplaza con el nombre de usuario real
            idRecipe: idRecipe,
            score: score,
        }),

    }).then((response) => response.json()
    ).then(function (obj) {
        // Maneja la respuesta del servidor si es necesario
        console.log(obj);
    }).catch(function (e) {
        console.error('Error al ingresar la puntuación: ', e);
    })


}

function enviarComentarios(username, idRecipe) {
    const textarea = document.querySelector('#box-comentarios');
    const comentario = textarea.value;

    fetch("/addComment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            username: username, // Reemplaza con el nombre de usuario real
            idRecipe: idRecipe,
            comment: comentario,
        }),

    }).then((response) => response.json()
    ).then(function (obj) {
        // Maneja la respuesta del servidor si es necesario
        console.log(obj);
    }).catch(function (e) {
        console.error('Error al ingresar comentarios: ', e);
    })
}