package com.example.gymactive.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.R

class SettingFragment : Fragment() {

    companion object {
        private const val PICK_IMAGE = 100  // Código de solicitud para abrir la galería
    }

    private lateinit var userNameEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var openGalleryButton: Button
    private lateinit var closeSettingsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        // Inicializar las vistas
        initializeViews(view)

        // Configurar los botones
        setupOpenGalleryButton()
        setupSaveButton()
        setupCloseSettingsButton()

        return view
    }

    // Función para inicializar las vistas
    private fun initializeViews(view: View) {
        userNameEditText = view.findViewById(R.id.userNameEditText)
        imageView = view.findViewById(R.id.imageView)
        saveButton = view.findViewById(R.id.saveButton)
        openGalleryButton = view.findViewById(R.id.openGalleryButton)
        closeSettingsButton = view.findViewById(R.id.cerrarAjustes)
    }

    // Configurar el botón para abrir la galería y seleccionar una imagen
    private fun setupOpenGalleryButton() {
        openGalleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }
    }

    // Configurar el botón para guardar el nombre y la imagen en las preferencias compartidas
    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            val userName = userNameEditText.text.toString()
            if (userName.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Obtener URI de la imagen
            val imageUri = imageView.tag?.toString() ?: ""

            // Guardar en SharedPreferences
            saveUserSettings(userName, imageUri)
        }
    }

    // Función para guardar los datos del usuario en SharedPreferences
    private fun saveUserSettings(userName: String, imageUri: String) {
        Log.d("SettingFragment", "Guardando nombre de usuario: $userName y URI de imagen: $imageUri")
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("UserSettings", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userName", userName)
        editor.putString("imageUri", imageUri)
        editor.apply()  // Aplicar los cambios

        Toast.makeText(requireContext(), "Configuración guardada", Toast.LENGTH_SHORT).show()
    }

    // Configurar el botón para cerrar los ajustes
    private fun setupCloseSettingsButton() {
        closeSettingsButton.setOnClickListener {
            // Lógica para volver al fragment anterior o cerrar el fragmento
            parentFragmentManager.popBackStack()
        }
    }

    // Método para manejar la selección de imagen desde la galería
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageView.setImageURI(imageUri)

            // Guardar la URI de la imagen en el Tag de la ImageView
            imageView.tag = imageUri.toString()
            Log.d("SettingFragment", "Imagen seleccionada: $imageUri")
        }
    }
}
