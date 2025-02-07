package com.example.gymactive.domain.Comidas.models

class ListComida private constructor(
){
    val comidasMutableList: MutableList<Comida> = mutableListOf()

    fun getComidas() = comidasMutableList

    fun getSize() = comidasMutableList.size

    fun getLastPos() = comidasMutableList.size -1

    companion object{
        val comidaObject: ListComida by lazy {
            ListComida()
        }
    }
}