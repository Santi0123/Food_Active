package com.example.gymactive.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gymactive.R

class SettingFragment : Fragment() {

    companion object {
        private const val PICK_IMAGE = 100  // Código de solicitud para abrir la galería
    }

    private lateinit var imageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var openGalleryButton: Button
    private lateinit var closeSettingsButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        initializeViews(view)
        sharedPreferences = requireContext().getSharedPreferences("UserSettings", AppCompatActivity.MODE_PRIVATE)
        loadUserSettings()
        setupOpenGalleryButton()
        setupSaveButton()
        setupCloseSettingsButton()
        return view
    }

    private fun initializeViews(view: View) {
        imageView = view.findViewById(R.id.imageView)
        saveButton = view.findViewById(R.id.saveButton)
        openGalleryButton = view.findViewById(R.id.openGalleryButton)
        closeSettingsButton = view.findViewById(R.id.cerrarAjustes)
    }

    private fun setupOpenGalleryButton() {
        openGalleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
        }
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            val imageUri = imageView.tag?.toString() ?: ""
            saveUserSettings(imageUri)
        }
    }

    private fun saveUserSettings(imageUri: String) {
        val editor = sharedPreferences.edit()
        editor.putString("imageUri", imageUri)
        editor.apply()
        Toast.makeText(requireContext(), "Configuración guardada", Toast.LENGTH_SHORT).show()
    }

    private fun loadUserSettings() {
        val imageUriString = sharedPreferences.getString("imageUri", "")
        if (!imageUriString.isNullOrEmpty()) {
            Glide.with(this)
                .load(Uri.parse(imageUriString))
                .into(imageView)
            imageView.tag = imageUriString
        }
    }

    private fun setupCloseSettingsButton() {
        closeSettingsButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            if (imageUri != null) {
                Glide.with(this)
                    .load(imageUri)
                    .into(imageView)
                imageView.tag = imageUri.toString()

                // Guarda la URI de la imagen en SharedPreferences
                val editor = sharedPreferences.edit()
                editor.putString("imageUri", imageUri.toString())
                editor.apply()

                // Mensaje de log para verificar que la URI se guarda correctamente
                Log.d("SettingFragment", "Imagen seleccionada: $imageUri")
            }
        }
    }

}
