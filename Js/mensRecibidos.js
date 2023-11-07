(function () {
    const usernameActive = window.localStorage.getItem("usermail");
    fetch("/api/inbox/"+ usernameActive, {
        headers: { "Content-Type": "application/json" },

    }).then(function (res) {
        return res.json();
    }).then(function (obj) {
        const content = document.querySelector('#messagesContainer');
        let html = "<div class='container row'>";

        obj.forEach((element) => {

                html += "<div class='col-mc-1'><div class='card card-body position-relative' style='width: 18rem;' style= 'display:block'>"
                html += "<p>Remitente: " + element.destination + "</p>"
                html += "<p>Mensaje: " + element.subject + "</p>"
               // html += "<div><button id='btn-fav-" + element.username + "' class='btn btn-primary ml-auto' onclick='toggleFavoritosEnServidor(\"" + usernameActive + "\",\"" + element.username + "\")' data-username='${element.username}' >" + claseBoton + "</button>"
                //html += "<button id='btn-enviar-mensaje-" + element.username + "' class='btn btn-primary ml-auto' onclick='redirectToEnviarMens(\"" + element.username + "\")' data-route='/enviarMens'>Enviar mensaje</button>"

                html += "</div>"
            
        })
        html += '</div>'
        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();