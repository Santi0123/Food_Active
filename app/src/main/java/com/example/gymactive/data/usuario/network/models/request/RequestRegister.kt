package com.example.gymactive.data.usuario.network.models.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RequestRegister (
    @SerializedName("email")
    @Expose
    val email:String,

    @SerializedName("password")
    @Expose
    val password:String
)