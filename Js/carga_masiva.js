const input = document.querySelector("input");
document.addEventListener("change", adjuntarCSV);
const submitButton = document.querySelector(".btn-success");
const username = window.localStorage.getItem("username");
const usernamePlaceholder = document.getElementById("usernamePlaceholder");
usernamePlaceholder.textContent = username;

function adjuntarCSV(e) {
    const file = e?.target.files[0];
    //const message = document.querySelector("h6");
    const span = document.querySelector("#boton-eliminar");
    const accept_types = ["application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel", "text/csv"];
    console.log(file);

    let html = "<button type='button' class='btn btn-secondary' onclick='eliminarArchivo()'>"
    html += "<svg xmlns='http://www.w3.org/2000/svg' width='16' height='16' fill='currentColor' class='bi bi-trash' viewBox='0 0 16 16'>"
    html += "<path d='M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z'></path>"
    html += "<path d='M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z'></path>"
    html += "</svg></button>"

    span.innerHTML = html;
    //message.textContent = `Adjuntaste: ${file.name}`;

    span.addEventListener("click", function () {
        input.value = "";
        submitButton.disabled = true; // Deshabilita el botón de envío cuando se elimina el archivo
    });

    // Habilita el botón de envío si el archivo es válido
    if (file && accept_types.includes(file.type)) {
        submitButton.disabled = false;
    }
}

function eliminarArchivo() {
    input.value = "";
    form.reset();
    submitButton.disabled = true; // Deshabilita el botón de envío cuando se elimina el archivo
}

function cargarCSV() {
    const file = input.files[0]; // Corrige el acceso a los archivos seleccionados

    if (!file) {
        window.alert('Por favor, seleccione un archivo CSV válido.');
        return;
    }

    const formData = new FormData();
    formData.append('borrador', file);

    const username = window.localStorage.getItem('username');
    fetch("/api/uploadFile/" + username, {
        method: 'POST',
        body: formData,
    })
        .then((response) => response.json())
        .then(function (data) {
            window.location.replace("/resumen_recetas");
        })
        .catch(function (error) {
            console.error('Error al cargar la receta al recetario:', error);
        });
}


