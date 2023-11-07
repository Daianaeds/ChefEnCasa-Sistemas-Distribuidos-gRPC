const usernameActive = window.localStorage.getItem("username");
const usernamePlaceholder = document.getElementById("usernamePlaceholder");
usernamePlaceholder.textContent = usernameActive;

//obtenemos el nombre del destinatario
const usermailActive = window.localStorage.getItem("usermail");
const usermailPlaceholder = document.getElementById("usermailPlaceholder");
usermailPlaceholder.textContent = usermailActive;

//escucha de boton
document.getElementById("mensajeForm").addEventListener("submit", function (event) {
    event.preventDefault(); 

    const asunto = document.getElementById("asunto").value;//asunto del mensaje
    const destinatarioUsername = usermailActive;

    fetch("/api/InternalMail/create", {//solicitud post
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            source: usernameActive,
            subject: asunto,
            destination: destinatarioUsername,
            subjectReply: "",
        }),
    })
        .then((response) => response.json())
        .then((data) => {
            console.log("Mensaje enviado:", data);
            alert("Mensaje enviado correctamente.");
            window.location.replace("/enviados");
        })
        .catch((error) => {
            console.error('Error al enviar el mensaje:', error);
        });
});
