package com.example.gymactive.data.usuario.repository

import com.example.gymactive.data.usuario.network.models.request.RequestLogin
import com.example.gymactive.data.usuario.network.models.request.RequestRegister
import com.example.gymactive.data.usuario.network.service.UsuarioApiService
import com.example.gymactive.domain.usuario.models.UsuarioModel
import com.example.gymactive.frameworks.InstanceRetrofit
import javax.inject.Inject

class UsuarioRepository @Inject constructor(private val service: UsuarioApiService){
    companion object{
        val repository: UsuarioRepository by lazy {
            UsuarioRepository(UsuarioApiService(InstanceRetrofit.getApiService()))
        }
    }

    suspend fun loginApi(email:String,password:String): UsuarioModel?{
        val usuarioRequest = RequestLogin(email, password)
        val result = service.login(usuarioRequest)

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

    suspend fun register(usuario: UsuarioModel): UsuarioModel?{
        val usuarioRequest = RequestRegister(usuario.email!!, usuario.password!!)
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