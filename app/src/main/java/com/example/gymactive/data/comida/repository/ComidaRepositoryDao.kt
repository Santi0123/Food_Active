package com.example.gymactive.data.comida.repository

import com.example.gymactive.data.comida.objects.ComidasData
import com.example.gymactive.domain.Comidas.interfaces.InterfacesDAO
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ListComida
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ComidaRepositoryDao @Inject constructor():InterfacesDAO {

    override suspend fun getNativeComida(): List<Comida> {
        return ComidasData.listaComidas
    }

    // Es la lista mutable la cual cambia
    override suspend fun getComidaList(): List<Comida> {
        return ListComida.comidaObject.comidasMutableList
}

    override suspend fun deleteComida(pos: Int): Boolean {
        return if (pos < ListComida.comidaObject.comidasMutableList.size){
            ListComida.comidaObject.comidasMutableList.removeAt(pos)
            true
        }
        else
            false
    }

    override suspend fun addComida(newComida: Comida): Comida? {
        ListComida.comidaObject.comidasMutableList.add(newComida)
        return newComida
    }

    override suspend fun updateComida(pos: Int, comida: Comida): Boolean {

        return if(pos < ListComida.comidaObject.comidasMutableList.size){
            ListComida.comidaObject.comidasMutableList[pos] = ListComida.comidaObject.comidasMutableList.get(pos).copy(
                nombre_plato = comida.nombre_plato,
                descripcion = comida.descripcion,
                image = comida.image
            )
            true
        }
        else
            false
    }

    override suspend fun exisComida(comida: Comida): Boolean {
        return ListComida.comidaObject.comidasMutableList.contains(comida)
    }

    override suspend fun getComidaById(id: Int): Comida? {
        return if(id < ListComida.comidaObject.comidasMutableList.size){
            ListComida.comidaObject.comidasMutableList.get(id)
        }else
            null
    }

    override fun getComidaByPos(pos: Int): Comida? {
        return if(pos < ListComida.comidaObject.comidasMutableList.size){
            ListComida.comidaObject.comidasMutableList.get(pos)
        }else
            null
    }

}


