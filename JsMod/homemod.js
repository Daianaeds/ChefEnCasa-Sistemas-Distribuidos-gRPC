(function (){
    const username = window.localStorage.getItem("username");
    const password = window.localStorage.getItem("password");
    if(!username||!password){
        window.location.replace("/");
    }
    const usernamePlaceholder = document.getElementById("usernamePlaceholder");
    usernamePlaceholder.textContent = username;
    
    fetch("/allComplaints", {
        method: "GET",
        headers: { "Content-Type": "application/json" },

    }).then(function(res){
        return res.json();
    }).then(function(obj){
        const content = document.querySelector('#content-denuncias'); 
        let html = "<div class='container row'>";       
            obj.forEach((element) => {

                html += "<div class='col-md-4'><div class='card card-body position-relative'>"
                html += "<ul>"
                element.forEach((denuncia)=>{
                    html += "<li>" + "● " + denuncia.username + " " + denuncia.motive;
                    html += "<a href = '/denuncia' role='button' onclick='guardarIdReceta("+ denuncia.idReceta +")'>Ver Detalles..</a>"
                    html += "</li>"
                    const complainingUser = windows.localStorage.setItem("complainingUser", denuncia.username);
                    const complainingMotive = windows.localStorage.setItem("complainingMotive", denuncia.motive);
                })
                html += "</ul>"  
                html += "</div></div>"
            })
            html += '</div>'
        content.innerHTML = html;
    }).catch(function(e){
        console.error(e);
    })
})();

function guardarIdReceta(id){
    windows.localStorage.setItem("idReceta", id);
}