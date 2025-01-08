package com.example.gymactive

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore

class Setting : AppCompatActivity() {

    companion object {
        private const val PICK_IMAGE = 100  // Código de solicitud para abrir la galería
    }

    private lateinit var userNameEditText: EditText
    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var openGalleryButton: Button
    private lateinit var closeSettingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)  // Asegúrate de que el layout sea el correcto

        // Inicializar las vistas
        initializeViews()

        // Configurar el botón para abrir la galería
        setupOpenGalleryButton()

        // Configurar el botón para guardar la configuración
        setupSaveButton()

        // Configurar el botón para cerrar la actividad y volver al MainActivity
        setupCloseSettingsButton()
    }

    // Función para inicializar las vistas
    private fun initializeViews() {
        userNameEditText = findViewById(R.id.userNameEditText)
        imageView = findViewById(R.id.imageView)
        saveButton = findViewById(R.id.saveButton)
        openGalleryButton = findViewById(R.id.openGalleryButton)
        closeSettingsButton = findViewById(R.id.cerrarAjustes)
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
                Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
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
        val sharedPreferences: SharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userName", userName)
        editor.putString("imageUri", imageUri)
        editor.apply()  // Aplicar los cambios

        Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show()
    }

    // Configurar el botón para cerrar los ajustes y regresar al MainActivity
    private fun setupCloseSettingsButton() {
        closeSettingsButton.setOnClickListener {
            // Crear un Intent para regresar al MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Finaliza esta actividad
        }
    }

    // Método para manejar la selección de imagen desde la galería
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            imageView.setImageURI(imageUri)

            // Guardar la URI de la imagen en el Tag de la ImageView
            imageView.tag = imageUri.toString()
        }
    }
}
