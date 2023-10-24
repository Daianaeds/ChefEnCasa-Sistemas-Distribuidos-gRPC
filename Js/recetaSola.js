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

        if (username !== obj.username) {
            html += "<label for='puntaje'>Calificar receta: </label>"
            html += "<select id='puntaje' name='puntaje'>"
            html += "<option value='1'>⭐</option>"
            html += "<option value='2'>⭐⭐</option>"
            html += "<option value='3'>⭐⭐⭐</option>"
            html += "<option value='4'>⭐⭐⭐⭐</option>"
            html += "<option value='5'>⭐⭐⭐⭐⭐</option></select>"
            html += "<button class='btn btn-success btn-sm' type='submit' onclick='enviarPuntaje(" + obj.id + ")'>Enviar</button>"
        }
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

        if (username !== obj.username) {
            html += "<label><strong>Agregar a recetario: </strong></label>"
            html += "<select id='recetario' name='recetario'></select>"; // Dejamos el select vacío para llenarlo dinámicamente
            html += "<button class='btn btn-success btn-sm' type='submit' onclick='cargarRecetario(" + obj.id + ")'>Agregar</button>"
            html += "</div>"

            html += "<label><strong>Denunciar receta por: </strong></label>"
            html += "<select id='denuncia' name='denuncia'>"
            html += "<option value='1'>Contenido inapropiado</option>"
            html += "<option value='2'>Ingredientes prohibidos</option>"
            html += "<option value='3'>Peligroso para la salud</option></select>"
            html += "<button class='btn btn-danger btn-sm' type='submit' onclick='enviarDenuncia(\"" + username + "\"," + obj.id + ")'>Denunciar</button>"
            html += "</div>"

            html += "<p class='card-text'><strong>Agregar comentarios:</strong></p>";
            html += "<div class='comment-container'>";
            html += "<textarea id='box-comentarios' class='col-md-6' rows='5' placeholder='Ingrese comentario..'></textarea>";
            html += "<button class='btn btn-primary btn-custom' type='submit' onclick='enviarComentarios(\"" + username + "\"," + obj.id + ")' >Enviar</button>";
            html += "</div id='comentarios'>";

        }

        html += "</div>";
        html += "<h5>Comentarios de los usuarios sobre la receta:</h5>"
        html += "<div class= 'card-body' >"
        if (Array.isArray(obj.comments) && obj.comments.length > 0) {
            obj.comments.forEach((comment) => {
                html += "<p><strong>" + comment.username + ": </strong> " + comment.comment + "</p>";
            });
        } else {
            html += "<h5 class= 'text-body-secondary'>Esta receta aún no posee comentarios</h5>"
        }

        html += "</div></div>"
        html += "</div></div>"

        content.innerHTML = html;

        function cargarRecetarios() {
            fetch("/api/recipebook/listRecipeBooks/" + username, {
                method: "GET",
                headers: { "Content-Type": "application/json" },
            })
                .then(function (res) {
                    return res.json();
                })
                .then(function (data) {
                    const recetarioSelect = document.querySelector('#recetario');
                    // Limpiar opciones anteriores, si las hay
                    recetarioSelect.innerHTML = "";
                    // Llenar el select con las opciones de recetarios
                    data.recipeBookList.forEach(function (recetario) {
                        const option = document.createElement('option');
                        option.value = recetario.id;
                        option.text = recetario.nameBook;
                        recetarioSelect.appendChild(option);
                    });
                })
                .catch(function (e) {
                    console.error(e);
                });
        }
        // Llama a la función para cargar la lista de recetarios después de cargar la receta
        cargarRecetarios();
    }).catch(function (e) {
        console.error(e);
    });
})();

function cargarRecetario(idRecipe) {
    const select = document.querySelector('#recetario');
    const idRecipeBook = select.value;

    fetch("/api/recipebook/add-recipe", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ // Reemplaza con el nombre de usuario real
            idRecipeBook: idRecipeBook,
            idRecipe: idRecipe,
        }),
    }).then((response) => response.json()
    ).then(function (obj) {
        window.alert("Receta agregada al recetario seleccionado!");
        console.log(obj);
    }).catch(function (e) {
        console.error('Error al cargar la receta al recetario: ', e);
    });

}

// Agregar a el código que me pasaste y adaptarlo a este codigo
function enviarPuntaje(idRecipe) {
    const select = document.querySelector('#denuncia');
    const score = select.value;

    fetch("/api/recipe/addStars", {
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
    });
}

function enviarDenuncia(username, idRecipe) {
    const select = document.querySelector('#puntaje');
    const motive = select.value;

    fetch("/api/denuncias/add/denunciation", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ // Reemplaza con el nombre de usuario real
            idRecipe: idRecipe,
            username: username,
            motive: motive,
        }),
    }).then((response) => response.json()
    ).then(function (obj) {
        window.alert("Denuncia enviada al moderador!");
        console.log(obj);
    }).catch(function (e) {
        console.error('Error al ingresar la puntuación: ', e);
    });
}

function enviarComentarios(username, idRecipe) {
    const textarea = document.querySelector('#box-comentarios');
    const comentario = textarea.value;

    fetch("/api/recipe/addComment", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            username: username, // Reemplaza con el nombre de usuario real
            idRecipe: idRecipe,
            comment: comentario,
        }),
    }).then((response) => {
        return response.json();
    }).then(function (obj) {
        console.log(obj);
        location.reload();
    }).catch(function (e) {
        console.error('Error al ingresar comentarios: ', e);
    });
}
