package com.example.gymactive

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private val MYUSER = "12"
    private val MYPASS = "12"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        enableEdgeToEdge()

        initUI()
    }
    private fun initUI(){
        loginBinding.aceptarBoton.setOnClickListener { validarUsuario() }
    }
    private fun validarUsuario() {
        val usuarioIngresado = loginBinding.textModNombre.editText?.text.toString()
        val contrasennaIngresada = loginBinding.textModPassword.editText?.text.toString()

        if (usuarioIngresado.isEmpty() || contrasennaIngresada.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese usuario y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        if (usuarioIngresado == MYUSER && contrasennaIngresada == MYPASS) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, getString(R.string.acceder_pagina), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.no_se_puede_acceder), Toast.LENGTH_SHORT).show()
        }
    }
}
