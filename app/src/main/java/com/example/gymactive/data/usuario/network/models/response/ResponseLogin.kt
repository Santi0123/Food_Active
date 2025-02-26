package com.example.gymactive.data.usuario.network.models.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("email")
    @Expose
    val email:String,

    @SerializedName("password")
    @Expose
    val password:String,

    @SerializedName("nombre")
    @Expose
    val nombre:String?= null,

    @SerializedName("imagen")
    @Expose
    val imagen:String?= null

    /*@SerializedName("token")
    @Expose
    val token:String?=null*/
)