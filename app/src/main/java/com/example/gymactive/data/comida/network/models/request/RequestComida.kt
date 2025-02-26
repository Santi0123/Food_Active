package com.example.gymactive.data.comida.network.models.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RequestComida(

    @SerializedName("usuarioId")
    @Expose
    val usuarioId:Int,

    @SerializedName("nombrePlato")
    @Expose
    val nombrePlato:String,

    @SerializedName("descripcion")
    @Expose
    val descripcion:String,

    @SerializedName("imagen")
    @Expose
    val imagen:String,

    @SerializedName("token")
    @Expose
    val token:String
) {
}