package com.example.gymactive.data.comida.network.services

import com.example.gymactive.data.comida.network.models.request.RequestComida
import com.example.gymactive.data.comida.network.models.response.ResponseComida
import javax.inject.Inject

class ComidaApiService @Inject constructor(private val apiService: ComidaApiServiceInterface) {

    suspend fun getAllComidas(): Result<List<ResponseComida>> {
        return try {
            val response = apiService.getAllComidas()
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("El cuerpo de la respuesta es nulo"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error al obtener las comidas: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getComidaById(id: Int): Result<ResponseComida> {
        return try {
            val response = apiService.getComidaById(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                }
                    ?: Result.failure(RuntimeException("Respuesta exitosa, pero el cuerpo está vacío"))
            } else {
                val errorMessage =
                    response.errorBody()?.string() ?: "Respuesta fallida sin detalles"
                Result.failure(RuntimeException("Error ${response.code()}: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(RuntimeException("Fallo en la solicitud: ${e.message}", e))
        }
    }


    suspend fun getComidasByUser(userId: Int): Result<List<ResponseComida>> {
        return try {
            val response = apiService.getComidasByUser(userId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("El cuerpo de la respuesta es nulo"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error al obtener las comidas del usuario: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchComidas(term: String): Result<List<ResponseComida>> {
        return try {
            val response = apiService.searchComidas(term)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("El cuerpo de la respuesta es nulo"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error en la búsqueda de comidas: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addComida(comida: RequestComida): Result<ResponseComida> {
        return try {
            val response = apiService.addComida(comida)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("El cuerpo de la respuesta es nulo"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error al agregar la comida: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateComida(id: Int, comida: RequestComida): Result<ResponseComida> {
        return try {
            val response = apiService.updateComida(id, comida)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("Respuesta exitosa, pero el cuerpo está vacío"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error al actualizar la comida: ${response.code()} - $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(RuntimeException("Fallo en la solicitud: ${e.message}", e))
        }
    }


    suspend fun deleteComida(id: Int): Result<Boolean> {
        return try {
            val response = apiService.deleteComida(id)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("El cuerpo de la respuesta es nulo"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error al eliminar la comida: ${response.code()} - $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(RuntimeException("Fallo en la solicitud: ${e.message}", e))
        }
    }

}
