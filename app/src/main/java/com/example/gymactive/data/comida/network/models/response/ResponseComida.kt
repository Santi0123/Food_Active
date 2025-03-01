package com.example.gymactive.data.comida.network.models.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseComida(
    @SerializedName("id")
    @Expose
    val id:Int,

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
    val imagen:String
) {
}