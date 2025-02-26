package com.example.gymactive.data.usuario.network.service

import com.example.gymactive.data.usuario.network.models.request.RequestLogin
import com.example.gymactive.data.usuario.network.models.request.RequestRegister
import com.example.gymactive.data.usuario.network.models.response.ResponseLogin
import com.example.gymactive.data.usuario.network.models.response.ResponseRegister
import javax.inject.Inject

class UsuarioApiService @Inject constructor(private val apiService: UsuarioApiServiceInterface) {

    suspend fun login(usuario: RequestLogin): Result<ResponseLogin> {
        return try {
            val response = apiService.login(usuario)  // Cambié 'respone' a 'response'
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("Response body is null"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error en el login: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun register(usuario: RequestRegister): Result<ResponseRegister> {
        return try {
            val response = apiService.register(usuario)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(RuntimeException("El cuerpo de la respuesta es nulo"))
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(RuntimeException("Error en el registro: $errorMessage"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
