package com.example.gymactive.di

import com.example.gymactive.data.comida.network.services.ComidaApiServiceInterface
import com.example.gymactive.data.usuario.network.service.UsuarioApiService
import com.example.gymactive.data.usuario.network.service.UsuarioApiServiceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Hilt lo usará en toda la aplicación
object NetworkModule {

    private const val BASE_URL = "http://192.168.1.146:8081/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideComidaApiServiceInterface(retrofit: Retrofit): ComidaApiServiceInterface {
        return retrofit.create(ComidaApiServiceInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UsuarioApiServiceInterface {
        return retrofit.create(UsuarioApiServiceInterface::class.java)
    }
}

