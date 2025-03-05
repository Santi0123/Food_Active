package com.example.gymactive.ui.views.activities

import com.example.gymactive.ui.viewmodel.usuario.UsuarioViewModel
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.databinding.FragmentRegistrarseBinding
import com.example.gymactive.domain.usuario.models.UsuarioModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Registrarse : AppCompatActivity() {

    private lateinit var binding: FragmentRegistrarseBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val shared: SharedPreferences by lazy {
        getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = FragmentRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
        initListener()
    }

    private fun setObserver() {
        usuarioViewModel.registerLiveData.observe(this) { usuario ->
            usuario?.let {
                savePreference(it)
                Toast.makeText(this, "Registro con éxito", Toast.LENGTH_SHORT).show()
                cleanBox()
                startActivity(Intent(this, Login::class.java))
                finish()
            } ?: run {
                resetShared()
                Toast.makeText(this, "Compruebe su correo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savePreference(usuario: UsuarioModel) {
        with(shared.edit()) {
            putString("email", usuario.email)
            putString("nombre", usuario.nombre)
            apply()
        }
    }

    private fun cleanBox() {
        binding.textEmail.text?.clear()
        binding.textPassword.text?.clear()
        binding.confirmarContrasenna.text?.clear()
    }

    private fun resetShared() {
        with(shared.edit()) {
            clear()
            apply()
        }
    }

    private fun initListener() {
        binding.aceptarBoton.setOnClickListener {
            val email = binding.textEmail.text.toString().trim()
            val password = binding.textPassword.text.toString()
            val repeatPassword = binding.confirmarContrasenna.text.toString()
            val privacyAccepted = binding.checkBox.isChecked

            when {
                !privacyAccepted ->
                    Toast.makeText(this, "Debe aceptar los términos y condiciones", Toast.LENGTH_LONG).show()

                email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty() ->
                    Toast.makeText(this, "No debe dejar campos vacíos", Toast.LENGTH_LONG).show()

                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_LONG).show()

                password != repeatPassword ->
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()

                else -> usuarioViewModel.register(email, password)
            }
        }
    }
}
