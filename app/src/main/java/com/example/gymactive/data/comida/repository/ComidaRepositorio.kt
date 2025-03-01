package com.example.gymactive.data.comida.repository


import com.example.gymactive.data.comida.network.models.request.RequestComida
import com.example.gymactive.data.comida.network.models.response.ResponseComida
import com.example.gymactive.data.comida.network.services.ComidaApiService
import com.example.gymactive.domain.Comidas.models.ComidaModel
import retrofit2.Response
import javax.inject.Inject

class ComidaRepositorio @Inject constructor(private val comidaApiService: ComidaApiService) {

    suspend fun getAllComidas(): Result<List<ComidaModel>> {
        return comidaApiService.getAllComidas().map{ comida->
            comida.map { it.toDomain() }
        }
    }

    suspend fun getComidaById(id: Int): Result<ComidaModel> {
        return comidaApiService.getComidaById(id).map { it.toDomain() }
    }

    suspend fun getComidasByUser(userId: Int): Result<List<ComidaModel>> {
        return comidaApiService.getComidasByUser(userId).map { comida->
            comida.map { it.toDomain() }
        }
    }

    suspend fun searchComidas(term: String): Result<List<ComidaModel>> {
        return comidaApiService.searchComidas(term).map { comida->
            comida.map{it.toDomain()}
        }
    }

    suspend fun addComida(comida: ComidaModel): Result<ComidaModel> {
        return comidaApiService.addComida(comida.toRequest()).map { it.toDomain() }
    }

    suspend fun updateComida(id: Int, comida: ComidaModel): Result<ComidaModel> {
        return comidaApiService.updateComida(id,comida.toRequest()).map { it.toDomain() }
    }

    suspend fun deleteComida(id: Int): Result<Boolean> {
        return comidaApiService.deleteComida(id)
    }


}
    private fun ResponseComida.toDomain():ComidaModel{
        return ComidaModel(
            id = this.id,
            usuarioId =this.usuarioId,
            nombrePlato = this.nombrePlato,
            descripcion = this.descripcion,
            imagen = this.imagen,
        )
    }

    private fun ComidaModel.toRequest():RequestComida{
        return RequestComida(
            usuarioId = this.usuarioId ?: throw  IllegalArgumentException("id del usuario null"),
            nombrePlato = this.nombrePlato ?: throw IllegalArgumentException("nombre plato null"),
            descripcion = this.descripcion ?: throw IllegalArgumentException("descripcion null"),
            imagen = this.imagen ?: "",
        )
    }