package com.example.gymactive

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gymactive.databinding.FragmentRegistrarseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class Registrarse : Fragment() {

    private var _binding: FragmentRegistrarseBinding? = null
    private val binding get() = _binding!!

    private lateinit var authentication: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authentication = FirebaseAuth.getInstance()

        binding.aceptarBoton.setOnClickListener {
            val email = binding.textModNombre.editText?.text.toString()
            val password = binding.textModPassword.editText?.text.toString()
            val repeatPassword = binding.confirmarContrasenna.editText?.text.toString()
            val privacyAccepted = binding.checkBox.isChecked

            if (!privacyAccepted) {
                Toast.makeText(context, "Debe aceptar los términos y condiciones", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(context, "No debe dejar campos vacíos", Toast.LENGTH_LONG).show()
            } else if (password != repeatPassword) {
                Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
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
                        Toast.makeText(context, "Registro exitoso. Verifique su correo electrónico.", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Registro exitoso, pero fallo al enviar verificación: ${emailTask.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                    authentication.signOut()
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
            Toast.makeText(context, "El usuario ya existe", Toast.LENGTH_LONG).show()
        } catch (e: FirebaseAuthWeakPasswordException) {
            Toast.makeText(context, "La contraseña es demasiado débil: ${e.reason}", Toast.LENGTH_LONG).show()
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Toast.makeText(context, "El email proporcionado no es válido", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
