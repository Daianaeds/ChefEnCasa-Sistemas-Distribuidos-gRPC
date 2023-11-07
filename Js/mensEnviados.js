(function () {

    const username = window.localStorage.getItem("username");
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;

    const usernameActive = window.localStorage.getItem("usermail");
    fetch("/api/InternalMail/sent/"+ username, {
        headers: { "Content-Type": "application/json" },

    }).then(function (res) {
        return res.json();
    }).then(function (obj) {
        const content = document.querySelector('#mensajesContainer');
        let html = "<div class='container row'>";

        obj.forEach((element) => {
                html += "<div class='col-mc-1'><div class='card card-body position-relative' style='width: 18rem;' style= 'display:block'>"
                html += "<p>Destinatario: " + element.destination + "</p>"
                html += "<p>Mensaje: " + element.subject + "</p>"
                html += "</div>"            
        })
        html += '</div>'
        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();

