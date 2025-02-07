package com.example.gymactive.ui.views.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.databinding.FragmentRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class Registrarse : AppCompatActivity() {

    private lateinit var binding: FragmentRegistrarseBinding
    private lateinit var authentication: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authentication = FirebaseAuth.getInstance()

        binding.aceptarBoton.setOnClickListener {
            val email = binding.textModNombre.editText?.text.toString()
            val password = binding.textModPassword.editText?.text.toString()
            val repeatPassword = binding.confirmarContrasenna.editText?.text.toString()
            val privacyAccepted = binding.checkBox.isChecked

            if (!privacyAccepted) {
                Toast.makeText(this, "Debe aceptar los términos y condiciones", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(this, "No debe dejar campos vacíos", Toast.LENGTH_LONG).show()
            } else if (password != repeatPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            } else {
                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = authentication.currentUser
                user?.sendEmailVerification()?.addOnCompleteListener { emailTask ->
                    if (emailTask.isSuccessful) {
                        Toast.makeText(this, "Registro exitoso. Verifique su correo electrónico.", Toast.LENGTH_LONG).show()
                        authentication.signOut()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Registro exitoso, pero fallo al enviar verificación: ${emailTask.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                handleRegistrationError(task.exception)
            }
        }
    }

    private fun handleRegistrationError(exception: Exception?) {
        try {
            throw exception ?: Exception("Error desconocido")
        } catch (e: FirebaseAuthUserCollisionException) {
            Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_LONG).show()
        } catch (e: FirebaseAuthWeakPasswordException) {
            Toast.makeText(this, "La contraseña es demasiado débil: ${e.reason}", Toast.LENGTH_LONG).show()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Toast.makeText(this, "El email proporcionado no es válido", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
