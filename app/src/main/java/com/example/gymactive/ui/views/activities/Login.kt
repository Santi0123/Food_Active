package com.example.gymactive.ui.views.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        enableEdgeToEdge()

        auth = Firebase.auth

        initUI()
    }

    private fun initUI() {
        loginBinding.aceptarBoton.setOnClickListener { startLogin() }
        loginBinding.registrarse.setOnClickListener { openRegisterActivity() }
        loginBinding.recordarContrasenna.setOnClickListener { recoverPassword() }
    }

    private fun openRegisterActivity() {
        val intent = Intent(this, Registrarse::class.java)
        startActivity(intent)
    }

    private fun startLogin() {
        val email = loginBinding.textModNombre.editText?.text.toString()
        val password = loginBinding.textModPassword.editText?.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese email y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user?.isEmailVerified == true) {
                    val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("is_logged_in", true)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                } else {
                    auth.signOut()
                    Toast.makeText(this, "Por favor, verifique su correo electrónico", Toast.LENGTH_SHORT).show()
                }
            } else {
                var message = "Error desconocido"
                try {
                    throw task.exception ?: Exception("Error desconocido")
                } catch (e: FirebaseAuthInvalidUserException) {
                    message = "El usuario no existe o ha sido deshabilitado"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    message = "Contraseña incorrecta"
                } catch (e: Exception) {
                    message = e.message.toString()
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun recoverPassword() {
        val email = loginBinding.textModNombre.editText?.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su email", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Email de recuperación enviado", Toast.LENGTH_SHORT).show()
            } else {
                var message = "Error desconocido"
                try {
                    throw task.exception ?: Exception("Error desconocido")
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    message = "Formato de email incorrecto"
                } catch (e: Exception) {
                    message = e.message.toString()
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
