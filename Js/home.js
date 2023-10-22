(function () {
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if (!username || !password) {
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;

    fetch("/lastFiveMessageNovedades", {
        method: "GET",
        headers: { "Content-Type": "application/json" },

    }).then(function (res) {
        return res.json();
    }).then(function (obj) {
        const content = document.querySelector('#content-ultimas-recetas');
        let html = "<div class='container row'>";
        obj.forEach((element) => {

            html += "<div class='col-md-4'><div class='card card-body position-relative'>"
            html += "<p>" + element.username + "</p>"
            html += "<h4 class='text-center'>" + element.title + "</h4>"

            if (element.url && element.url.url) {
                // Verificar que 'element.url' y 'element.url.url' estén definidos antes de acceder
                html += "<img src='" + element.url.url + "' alt='img-i' class='text-center imagen-receta'/>"
            } else {
                // Manejar el caso en que 'element.url' o 'element.url.url' no están definidos
                html += "<p>URL no disponible</p>"
            }
            html += "</div></div>"
        })
        html += '</div>'
        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();