(function () {
    const usernameActive = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!usernameActive || !password) {
        window.location.replace("/");
    }

    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = usernameActive;

    fetch("/api/recipebook/listRecipeBooks/" + usernameActive, {
        method: "GET",
        headers: { "Content-Type": "application/json" },

    }).then(function (res) {
        return res.json();
    }).then(function (obj) {
        const content = document.querySelector('#content-recetarios');
        let html = "<button class='btn btn-secondary' onclick='abrirModal()'>Crear recetario</button>"
        html += "<div class='col-md-12'><div class='card card-body position-relative'>"
        var i = 1;
        obj.recipeBookList.forEach((element) => {
            html += "<ul>"
            html += "<ol><strong>" + i + " - Recetario:  " + "</strong>" + element.nameBook + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
            html += "<a href='/recetasRecetario' role='button' onclick='guardarDatos(" + element.id + ")'>Ver recetario...</a></ol>";
            html += "</ul>"
            i++;
        })
        html += "</div></div>"
        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();

function guardarDatos(idRecetario) {
    window.localStorage.setItem("idRecetario", idRecetario);
}

// Función para abrir el modal
function abrirModal() {
    const content = document.querySelector('#modal');
    let html = "<div class='modal'>";
    html += "<form id='recetarioForm' class='modal-form'>";
    html += "<div class='modal-content position-relative'>";
    html += "<h5>Crear recetario</h5>";
    html += "<div>";
    html += "<input type='text' id='nombreRecetario' class='form-control' placeholder='Nombre del recetario' required>";
    html += "</div>";
    html += "<p id='mensaje'></p>";
    html += "<div>";
    html += "<div><button class='btn btn-secondary modal-button' onclick='cerrarModal()'>Cancelar</button>"
    html += "<button class='btn btn-primary modal-button' onclick='crearRecetario()'>Crear</button></div></div>"
    html += "</div>";
    html += "</div>";
    html += "</form></div>";
    content.innerHTML = html;

    document.getElementById('modal').style.display = 'block';
}


// Función para cerrar el modal
function cerrarModal() {
    document.getElementById('modal').style.display = 'none';
}


// Función para crear el recetario (debes agregar la lógica de tu fetch)
function crearRecetario() {
    const input = document.querySelector('#nombreRecetario');
    const nombreRecetario = input.value;
    const usernameActive = window.localStorage.getItem("username");

    fetch("/api/recipebook/save/recipebooks", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            name: nombreRecetario,
            username: usernameActive,
        }),

    }).then(function (e) {
        if (e.ok) {
            location.reload();
            cerrarModal();
            window.alert("Registro exitoso!");
        }
    })
        .catch(function (e) {
            console.error(e);
            document.getElementById("error").innerHTML = "Rompio";
            window.alert("No creo la receta");
        });


}
