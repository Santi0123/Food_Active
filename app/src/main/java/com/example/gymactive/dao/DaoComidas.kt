package com.example.gymactive.dao

import com.example.gymactive.interfaces.InterfacesComidaDao
import com.example.gymactive.models.Comida
import com.example.gymactive.repository.RepositoryComida

class DaoComidas private constructor(): InterfacesComidaDao {

    companion object {
        val myDao: DaoComidas by lazy {
            DaoComidas()
        }
    }

    override fun getDataComida(): List<Comida>  = RepositoryComida.listaComidasPersonas
}