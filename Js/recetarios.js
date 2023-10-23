(function () {
    const usernameActive = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!usernameActive || !password) {
        window.location.replace("/");
    }

    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = usernameActive;

    fetch("/listRecipeBooks/" + usernameActive, {
        method: "GET",
        headers: { "Content-Type": "application/json" },

    }).then(function (res) {
        return res.json();
    }).then(function (obj) {
        const content = document.querySelector('#content-recetarios');
        let html = "<button class='btn btn-secondary' onclick='abrirModal()'>Crear recetario</button>"
        html += "<div class='col-md-12 max-width: 800px'><div class='card card-body position-relative'>"
        var i = 1;
        obj.recipeBookList.forEach((element) => {
            html += "<ul>"
            html += "<ol><strong>" + i + " - Recetario:  " + "</strong>" + element.nameBook + "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
            html += "<a href='/recetasRecetario' role='button' onclick='guardarIdRecetario(" + element.id + ")'>Ver recetario...</a></ol>"
            html += "</ul>"
            i++;
        })
        html += "</div></div>"
        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();

function guardarIdRecetario(idRecetario) {
    window.localStorage.setItem("idRecetario", idRecetario);
}

// Función para abrir el modal
function abrirModal() {
    document.getElementById('modal').style.display = 'block';
}

// Función para cerrar el modal
function cerrarModal() {
    document.getElementById('modal').style.display = 'none';
}

// Función para crear el recetario (debes agregar la lógica de tu fetch)
function crearRecetario() {
    const nombreRecetario = document.getElementById('nombreRecetario').value;
    // Agregar lógica para crear el recetario con el nombre proporcionado
    // Puedes usar un fetch aquí para enviar la solicitud al servidor
    // Luego, cierra el modal
    cerrarModal();
}
