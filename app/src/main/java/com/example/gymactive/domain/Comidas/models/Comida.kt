package com.example.gymactive.domain.Comidas.models

data class Comida(
    var id: Int,
    var nombre_plato: String,
    var descripcion: String,
    var image: String
){
    override fun toString(): String {
        return "Comida('id=$id',nombre_plato='$nombre_plato', descricion='$descripcion', image='$image')"
    }
}