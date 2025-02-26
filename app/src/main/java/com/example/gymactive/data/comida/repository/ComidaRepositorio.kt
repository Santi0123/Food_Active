package com.example.gymactive.data.comida.repository


import com.example.gymactive.data.comida.network.models.request.RequestComida
import com.example.gymactive.data.comida.network.models.response.ResponseComida
import com.example.gymactive.data.comida.network.services.ComidaApiService
import retrofit2.Response
import javax.inject.Inject

class ComidaRepositorio @Inject constructor(private val comidaApiService: ComidaApiService) {

    suspend fun getAllComidas(): Result<List<ResponseComida>> {
        return comidaApiService.getAllComidas()
    }

    suspend fun getComidaById(id: Int): Result<ResponseComida> {
        return comidaApiService.getComidaById(id)
    }

    suspend fun getComidasByUser(userId: Int): Result<List<ResponseComida>> {
        return comidaApiService.getComidasByUser(userId)
    }

    suspend fun searchComidas(term: String): Result<List<ResponseComida>> {
        return comidaApiService.searchComidas(term)
    }

    suspend fun addComida(comida: RequestComida): Result<ResponseComida> {
        return comidaApiService.addComida(comida)
    }

    suspend fun updateComida(id: Int, comida: RequestComida): Result<ResponseComida> {
        return comidaApiService.updateComida(id, comida)
    }

    suspend fun deleteComida(id: Int): Result<Boolean> {
        return comidaApiService.deleteComida(id)
    }
}
