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
import androidx.lifecycle.lifecycleScope
import com.example.gymactive.databinding.ActivityLoginBinding
import com.example.gymactive.domain.usuario.models.UsuarioModel
import com.example.gymactive.ui.views.fragment.VistaGeneral.VistaGeneralFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Login : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    val usuarioViewModel: UsuarioViewModel by viewModels()
    lateinit var shared: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        shared = getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        setObserver()

        if (isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            initListener()
        }
    }

    private fun setObserver() {
        usuarioViewModel.loginLiveData.observe(this) { usuario ->
            if (usuario != null) {
                savePreference(usuario)
                Toast.makeText(this, "login con éxito", Toast.LENGTH_SHORT).show()
                cleanBox()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                resetShared()
                Toast.makeText(this, "Compruebe su correo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun savePreference(usuario: UsuarioModel) {
        with(shared.edit()) {
            putInt("userId", usuario.id!!)
            putString("email", usuario.email)
            putString("nombre", usuario.nombre)
            putString("imagen",usuario.imagen)
            putString("token", usuario.token) // Guardar el token en SharedPreferences
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    private fun resetShared() {
        with(shared.edit()) {
            putInt("userId", -1)
            putString("email", "")
            putString("nombre", "")
            putString("imagen","")
            putBoolean("is_logged_in", false)
            remove("token") // Eliminar el token de SharedPreferences
            apply()
        }
    }

    private fun cleanBox() {
        loginBinding.textEmail.text?.clear()
        loginBinding.textPassword.text?.clear()
    }

    private fun isLoggedIn(): Boolean {
        return shared.getBoolean("is_logged_in", false)
    }

    private fun initListener() {
        loginBinding.aceptarBoton.setOnClickListener {

            val email = loginBinding.textEmail.text.toString().trim()
            val password = loginBinding.textPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this, "Todos los campos deben de estar rellenos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                lifecycleScope.launch {
                    usuarioViewModel.login(email, password)
                }
            }
        }

        loginBinding.sinRecordar.setOnClickListener {
            resertPassword()
        }

        loginBinding.registrarse.setOnClickListener {
            cleanBox()
            startActivity(Intent(this, Registrarse::class.java))
            finish()
        }

        loginBinding.invitado.setOnClickListener{
            cleanBox()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun resertPassword() {
        Toast.makeText(this, "Contacte con el admin", Toast.LENGTH_SHORT).show()
    }
}
