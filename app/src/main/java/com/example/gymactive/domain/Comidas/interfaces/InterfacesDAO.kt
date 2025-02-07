package com.example.gymactive.domain.Comidas.interfaces

import com.example.gymactive.domain.Comidas.models.Comida

interface InterfacesDAO {
    suspend fun  getNativeComida(): List<Comida>
    suspend fun getComidaList(): List<Comida>
    suspend fun deleteComida(id:Int): Boolean
    suspend fun addComida(comida: Comida): Comida?
    suspend fun updateComida(pos:Int, comida: Comida): Boolean
    suspend fun exisComida(comida: Comida): Boolean
    suspend fun getComidaById(id:Int):Comida?
    fun getComidaByPos(pos:Int):Comida?
}