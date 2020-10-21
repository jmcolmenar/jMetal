/*
Documento       : navbar.js / jMetal GUI
Descripción     : Código javascript que controla el comportamiento responsive de la barra de navegación
Entidad         : Universidad Rey Juan Carlos
Autor           : Alejandro Manuel Pazos Boquete
*/

const toggleButton = document.querySelector('.toggle_button')
const navbarlinks = document.querySelector('.navbar_links')

toggleButton.addEventListener("click", () => {
    navbarlinks.classList.toggle("active");
});
