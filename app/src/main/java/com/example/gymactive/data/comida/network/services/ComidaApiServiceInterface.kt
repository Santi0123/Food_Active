package com.example.gymactive.data.comida.network.services

import com.example.gymactive.data.comida.network.models.request.RequestComida
import com.example.gymactive.data.comida.network.models.response.ResponseComida
import retrofit2.Response
import retrofit2.http.*

interface ComidaApiServiceInterface {

     @GET("comidas")
     suspend fun getAllComidas(): Response<List<ResponseComida>>

     @GET("comidas/{id}")
     suspend fun getComidaById(@Path("id") id: Int): Response<ResponseComida>

     @GET("comidas/user/{userId}")
     suspend fun getComidasByUser(@Path("userId") userId: Int): Response<List<ResponseComida>>

     @GET("comidas/search")
     suspend fun searchComidas(@Query("term") term: String): Response<List<ResponseComida>>

     @POST("comidas")
     suspend fun addComida(@Body comida: RequestComida): Response<ResponseComida>

     @PATCH("comidas/{id}")
     suspend fun updateComida(@Path("id") id: Int, @Body comida: RequestComida): Response<ResponseComida>

     @DELETE("comidas/{id}")
     suspend fun deleteComida(@Path("id") id: Int): Response<Boolean>

}