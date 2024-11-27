package com.example.gymactive.interfaces

import com.example.gymactive.models.Comida

interface InterfacesComidaDao {
    fun getDataComida(): List<Comida>
    // le añadimos la funcion de borrar
    fun deleteComida(comida: Comida)
}