// En src/main/java/com/example/gymactive/di/NetworkModule.kt
package com.example.gymactive.di

import com.example.gymactive.data.comida.network.services.ComidaApiServiceInterface
import com.example.gymactive.data.usuario.network.service.UsuarioApiServiceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "http://192.168.1.146:8081/"

    @Provides
    @Singleton
    fun provideTokenProvider(): TokenProvider {
        return TokenProvider()
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(tokenProvider: TokenProvider): TokenInterceptor {
        return TokenInterceptor(tokenProvider)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
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
