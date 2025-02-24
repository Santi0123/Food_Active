package com.example.gymactive.frameworks

import com.example.gymactive.data.usuario.network.service.UsuarioApiServiceInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanceRetrofit {
    private const val URL_BASE_RETROFIT = "http://localhost:8081/"

    private val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(URL_BASE_RETROFIT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service: UsuarioApiServiceInterface by lazy {
        retrofit.create(UsuarioApiServiceInterface::class.java)
    }

    fun getApiService(): UsuarioApiServiceInterface = service
}