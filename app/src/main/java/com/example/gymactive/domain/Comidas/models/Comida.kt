package com.example.gymactive.domain.Comidas.models

data class Comida(
    var id: Long,
    var nombre_plato: String,
    var descricion: String,
    var image: String
){
    override fun toString(): String {
        return "Comida('id=$id',nombre_plato='$nombre_plato', descricion='$descricion', image='$image')"
    }
}