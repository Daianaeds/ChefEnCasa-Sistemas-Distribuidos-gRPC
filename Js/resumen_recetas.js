(function () {
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!username || !password) {
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;

    let listaBorradores = []; // Variable para almacenar todas las recetas incompletas
    let paginaActual = 0; // Página actual
    const recetasPorPagina = 1; // Cantidad de recetas por página

    function mostrarReceta(receta) {
        // Obten los elementos del DOM para cada campo del formulario
        const tituloInput = document.querySelector('input[name="title"]');
        const descripcionTextarea = document.querySelector('textarea[name="description"]');
        const categoriaRadios = document.querySelectorAll('input[name="name_category"]');
        const tiempoInput = document.querySelector('input[name="time_minutes"]');

        // Actualiza los campos con los datos de la receta
        tituloInput.value = receta.title;
        descripcionTextarea.value = receta.description;

        // Marca el radio button correspondiente a la categoría
        categoriaRadios.forEach(radio => {
            if (radio.value === receta.category) {
                radio.checked = true;
            } else {
                radio.checked = false;
            }
        });

        tiempoInput.value = receta.time;
    }


    function mostrarRecetasDePagina() {
        const recetasPagina = listaBorradores.slice(paginaActual * recetasPorPagina, (paginaActual + 1) * recetasPorPagina);
        if (recetasPagina.length > 0) {
            mostrarReceta(recetasPagina[0]); // Mostrar la primera receta de la página
        } else {
            // Limpiar la interfaz si no hay recetas en la página
        }
    }

    function mostrarPaginaSiguiente() {
        if (paginaActual < Math.floor(listaBorradores.length / recetasPorPagina)) {
            paginaActual++;
            mostrarRecetasDePagina();
        }
    }

    function mostrarPaginaAnterior() {
        if (paginaActual > 0) {
            paginaActual--;
            mostrarRecetasDePagina();
        }
    }

    fetch("/api/incompleteRecipes/" + username, {
        headers: { "Content-Type": "application/json" },
    }).then(function (res) {
        return res.json();
    }).then(data => {
        listaBorradores = data; // Guarda los datos en la variable

        // Mostrar la primera página de recetas al cargar la página
        mostrarRecetasDePagina();
    }).catch(function (e) {
        console.error(e);
    })

    // Agregar eventos a los botones de navegación
    const btnPaginaAnterior = document.getElementById("btnPaginaAnterior");
    const btnPaginaSiguiente = document.getElementById("btnPaginaSiguiente");

    btnPaginaAnterior.addEventListener("click", mostrarPaginaAnterior);
    btnPaginaSiguiente.addEventListener("click", mostrarPaginaSiguiente);
})();



function createArrayToPicture(array) {
    const newArray = array.map((item) => {
        return { url: item };
    });
    return newArray;
}
function postRecipe(ev, from) {
    var picturesInput = document.querySelector(
        'input[name="pictures"]'
    ).value;
    var title = document.querySelector('input[name="title"]').value;
    var description = document.querySelector(
        'textarea[name="description"]'
    ).value;
    var steps = document.querySelector('textarea[name="steps"]').value;
    var time_minutes = document.querySelector(
        'input[name="time_minutes"]'
    ).value;
    var name_category = document.querySelector(
        'input[name="name_category"]:checked'
    ).value;
    var ingredients = listadoIngredientes; // Usamos el array listadoIngredientes
    var pictures = [];

    //valida si esta vacio y si posee datos, agrega las imagenes y las divide en comas
    if (picturesInput.trim() !== "") {
        const picturesArray = picturesInput.split(",");
        const isArray =
            picturesArray.length > 0 ? picturesArray : picturesInput.split();

        if (isArray.length > 5) {
            document.getElementById("error").innerHTML =
                "Error - No debe haber más de 5 imágenes";
            return;
        } else {
            var newPictureArray = createArrayToPicture(picturesArray);
        }
    }

    fetch("/api/recipe/save-recipe", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            auth: {
                username: username,
                password: password,
            },
            title: title,
            description: description,
            steps: steps,
            time_minutes: time_minutes,
            name_category: name_category,
            ingredients: ingredients,
            pictures: newPictureArray,
        }),
    })
        .then(function (e) {
            if (e.ok) {
                var auth = {
                    username: username,
                    password: password,
                };
                var authJSON = JSON.stringify(auth);

                window.alert("Receta publicada");
                window.location.replace("/recetas");
            } else {
                console.log(e);
                window.alert("No completaste todos los datos");
            }
        })
        .catch(function (e) {
            console.error(e);
            //document.getElementById("error").textContent = "Rompio";
            document.getElementById("error").textContent = "Error en la solicitud: " + error.message;
        });
}

function agregarIngrediente() {
    var ingrediente = document.getElementById("ingrediente").value;
    var cantidad = document.getElementById("cantidad").value;

    listadoIngredientes.push({ nombre: ingrediente, cantidad: cantidad });

    // Limpiar el contenido actual de "listadoIngredientes".
    const content = document.getElementById("listadoIngredientes");
    content.innerHTML = "";

    // Iterar sobre listadoIngredientes y agregar elementos a la lista.
    listadoIngredientes.forEach(function (ingrediente) {
        const li = document.createElement("li");
        li.textContent = `${ingrediente.nombre} ${ingrediente.cantidad}`;
        content.appendChild(li);
    });

    // Limpiar los campos de entrada después de agregar los datos.
    document.getElementById("ingrediente").value = "";
    document.getElementById("cantidad").value = "";
}