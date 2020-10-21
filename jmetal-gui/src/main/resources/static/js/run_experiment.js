/*
Documento       : run_experiment.js / jMetal GUI
Descripción     : Código javascript que controla el comportamiento del formulario de experimento.
Entidad         : Universidad Rey Juan Carlos
Autor           : Alejandro Manuel Pazos Boquete
*/

function selected_check(that) {
    if (that.value != "none") {
        document.getElementById("crossover_operator_values").style.display = "flex";
        document.getElementById("crossover_operator_values").style.justifyItems = "center";
    }
}

function selected_check2(that) {
    if (that.value != "none") {
        document.getElementById("mutation_operator_values").style.display = "flex";
        document.getElementById("mutation_operator_values").style.justifyItems = "center";
    }
}