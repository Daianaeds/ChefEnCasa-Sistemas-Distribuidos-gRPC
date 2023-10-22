(function (){
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if(!username||!password){
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;
    
    fetch("/favouriteRecipe/"+username, {
        headers: { "Content-Type": "application/json" },

    }).then(function(res){
        return res.json();
    }).then(function(obj){
        // Guarda los datos en una variable
        const content = document.querySelector('#content-receta'); 
        let html = "<div class='col-md-12 row'>";      
            obj.recipe.forEach((element) => {

                var claseEstrella = "far fa-star"; // far fa-star -> desactivada

                var recetaEncontrada = obj.recipe.find(function(receta) {
                    return receta.id === element.id;
                    });

                if(recetaEncontrada){
                    claseEstrella = "fas fa-star"; // fas fa-star -> activada
                }

                html += "<div class='col-md-3 element'><div class='card card-body position-relative'>";
                    html += "<div class='content-container'>";
                    html += "<h4 class='text-center'>"+element.title+"</h4>";
                    html += "<button id='btn-fav-"+element.id+"' class='btn btn-link favorite-btn "+claseEstrella+"' onclick='toggleFavoritosEnServidor(\"" + username + "\","+element.id+")' data-idrecipe='${element.id}'></button>";
                    html += "</div>";
                    html += "<img src='"+element.pictures[0].url+"' alt='img-i' class='text-center imagen-receta' style='vertical-align: middle;' />";
                    var truncatedDescription = element.description.substring(0, 50);
                    html += "<p>"+truncatedDescription+"... "+"<a href='/recetaSola' role='button' onclick='guardarIdReceta(" + element.id + ")'>Ver más</a></p>";
                    html += "</div></div>";
            })
            html += '</div>'
        content.innerHTML = html;
  
      }).catch(function(e){
        console.error(e);
    })
})();

function guardarIdReceta(id){
    window.localStorage.setItem("idReceta", id);
}

function toggleFavoritosEnServidor(username, idRecipe) {
    var elemento = document.getElementById("btn-fav-"+idRecipe);
    var endpoint;

    if (elemento.classList.contains("far")) {
        elemento.classList.remove("far");
        elemento.classList.add("fas");
        elemento.classList.add("fa-star");
        endpoint = "/follow-recipe";
    } else {
        elemento.classList.remove("fas");
        elemento.classList.remove("fa-star");
        elemento.classList.add("far");
        elemento.classList.add("fa-star");
        endpoint = "/unfollow-recipe";
    }
    
    // Realiza una solicitud HTTP POST al servidor para guardar los favoritos
    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            username: username, // Reemplaza con el nombre de usuario real
            idRecipe: idRecipe,
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