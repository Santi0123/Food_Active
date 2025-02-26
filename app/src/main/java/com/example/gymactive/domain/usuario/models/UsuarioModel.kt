package com.example.gymactive.domain.usuario.models


class UsuarioModel(
    val id:Int?=null,
    val email:String?=null,
    val password:String?=null,
    val nombre:String?=null,
    val imagen:String?=null,
    val token:String?=null
) {
    /**
     * Sirve tanto para el login como para el registro
     */
    constructor(email: String,password: String):this(
       null,
        email,
        password,
        null,
        null,
        null
    )

}