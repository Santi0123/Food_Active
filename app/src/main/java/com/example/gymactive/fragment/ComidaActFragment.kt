package com.example.gymactive.fragment

import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.gymactive.Login
import com.example.gymactive.R
import com.example.gymactive.controller.Controller
import com.example.gymactive.databinding.FragmentComidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ComidaActFragment : Fragment() {

    private var _binding: FragmentComidaBinding? = null
    private val binding get() = _binding!!
    lateinit var controller: Controller
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    private val openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            data?.data?.let { uri ->
                controller.imageUri = uri
                controller.dialogAgregarComida?.setImageUri(uri) // Actualiza la URI de la imagen en el diálogo
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Configuración del Controller
        controller = Controller(requireContext(), openGalleryLauncher)
        controller.setAdapter(binding)
        controller.initBotonAgregar(binding, this)
        autenticacion()
    }


    private fun autenticacion() {
        auth = Firebase.auth
        sharedPreferences = requireContext().getSharedPreferences("session_prefs", MODE_PRIVATE)
        // Verifica si hay una sesión iniciada en SharedPreferences
        val isLogged = sharedPreferences.getBoolean("is_logged_in", false)

        // Verifica si el usuario está autenticado
        val currentUser = auth.currentUser
        if (!isLogged || currentUser == null || !currentUser.isEmailVerified) {
            //Toast.makeText(this, "Redirigiendo a login.", Toast.LENGTH_LONG).show()
            startActivity(Intent(requireContext(), Login::class.java))
            requireActivity().finish()
        }
        val userName = requireActivity().findViewById<TextView>(R.id.userNameTextView)
        val userEmail = requireActivity().findViewById<TextView>(R.id.userEmailTextView)
        if(currentUser != null){
            val emailUser = currentUser.email.toString().split("0")
            userName.setText(emailUser[0])
            userEmail.setText(currentUser.email.toString())
        }
    }
}
