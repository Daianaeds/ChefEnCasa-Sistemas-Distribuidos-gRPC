(function () {

    const username = window.localStorage.getItem("username");
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;

    const usernameActive = window.localStorage.getItem("usermail");
    fetch("/api/inbox/"+ username, {
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
                html += "<div><button class='btn btn-primary ml-auto' onclick='redirectToEnviarMens(\"" + element.destination + "\")' > "
                html += "Responder</button></div></div>"            
        })
        html += '</div>'
        content.innerHTML = html;
    }).catch(function (e) {
        console.error(e);
    })
})();

function redirectToEnviarMens(destination){
    window.localStorage.setItem("destination", destination);
    window.location.replace("/enviarMens");
}