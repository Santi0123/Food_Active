package com.example.gymactive.models

data class Comida(
    var nombre_plato: String,
    var descricion: String,
    var image: String
){
    override fun toString(): String {
        return "Comida(nombre_plato='$nombre_plato', descricion='$descricion', image='$image')"
    }
}
