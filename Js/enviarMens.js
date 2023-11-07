const usernameActive = window.localStorage.getItem("username");
const usernamePlaceholder = document.getElementById("usernamePlaceholder");
usernamePlaceholder.textContent = usernameActive;

/*obtiene el nombre del usuario destinatario*/ 
const usermailActive = window.localStorage.getItem("usermail"); 
const usermailPlaceholder = document.getElementById("usermailPlaceholder");
usermailPlaceholder.textContent = usermailActive;

// Escucha el evento "submit" del formulario de mensajes
document.getElementById("mensajeForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Previene el envío del formulario por defecto

    // Obtiene el contenido del asunto y mensaje
    const asunto = document.getElementById("asunto").value;

    // Obtén el nombre de usuario del destinatario desde el elemento "usermailPlaceholder"
    const destinatarioUsername = usermailActive;

    // Realiza una solicitud POST para enviar el mensaje
    fetch("/api/InternalMail/create", {
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
            // Maneja la respuesta del servidor si es necesario
            console.log("Mensaje enviado:", data);

            // Puedes hacer algo aquí, como mostrar un mensaje de confirmación
            alert("Mensaje enviado correctamente.");
            window.location.replace("/recibidos");
        })
        .catch((error) => {
            console.error('Error al enviar el mensaje:', error);
        });
});
