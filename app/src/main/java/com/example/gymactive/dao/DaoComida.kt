package com.example.gymactive.dao

import com.example.gymactive.interfaces.InterfacesComidaDao
import com.example.gymactive.models.Comida
import com.example.gymactive.repository.RepositoryComida

class DaoComida private constructor(): InterfacesComidaDao {

    companion object {
        val myDao: DaoComida by lazy {
            DaoComida()
        }
    }

    override fun getDataComida(): List<Comida>  = RepositoryComida.listaComidas
    // Implemtamos la funcion de borrar
    override fun deleteComida(comida: Comida) { RepositoryComida.listaComidas.remove(comida) }
}