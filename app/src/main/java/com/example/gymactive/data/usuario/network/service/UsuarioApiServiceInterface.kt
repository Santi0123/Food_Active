package com.example.gymactive.data.usuario.network.service

import com.example.gymactive.data.usuario.network.models.request.RequestLogin
import com.example.gymactive.data.usuario.network.models.request.RequestRegister
import com.example.gymactive.data.usuario.network.models.response.ResponseLogin
import com.example.gymactive.data.usuario.network.models.response.ResponseRegister
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiServiceInterface {
    @POST("usuario/login")
    suspend fun login(@Body usuario: RequestLogin): Response<ResponseLogin>

    @POST("usuario/register")
    suspend fun register(@Body usuario: RequestRegister): Response<ResponseRegister>

}