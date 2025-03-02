package com.example.gymactive.data.usuario.repository

import com.example.gymactive.data.usuario.network.models.request.RequestLogin
import com.example.gymactive.data.usuario.network.models.request.RequestRegister
import com.example.gymactive.data.usuario.network.service.UsuarioApiService
import com.example.gymactive.di.TokenProvider
import com.example.gymactive.domain.usuario.models.UsuarioModel
import javax.inject.Inject

class UsuarioRepository @Inject constructor(
    private val service: UsuarioApiService,
    private val tokenProvider: TokenProvider
) {

    suspend fun loginApi(email: String, password: String): UsuarioModel? {
        val usuarioRequest = RequestLogin(email, password)
        val result = service.login(usuarioRequest)

        result
            .onSuccess { resUser ->
                tokenProvider.token = resUser.token // Almacenar el token en TokenProvider
                return UsuarioModel(
                    resUser.id.toInt(),
                    resUser.email,
                    "",
                    resUser.nombre,
                    resUser.imagen,
                    resUser.token // Almacenar el token en el modelo de usuario
                )
            }
            .onFailure {
                println("${it.message}")
            }
        return null
    }

    suspend fun registerApi(email: String, password: String): UsuarioModel? {
        val usuarioRequest = RequestRegister(email, password)
        val result = service.register(usuarioRequest)
        result
            .onSuccess { resUser ->
                return UsuarioModel(
                    resUser.id.toInt(),
                    resUser.email,
                    "",
                    resUser.nombre,
                    resUser.imagen,
                    ""
                )
            }
            .onFailure {
                println("${it.message}")
            }
        return null
    }
}
