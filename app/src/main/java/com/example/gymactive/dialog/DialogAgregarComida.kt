package com.example.gymactive.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.DialogFragment
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.gymactive.fragment.ComidaActFragment

class DialogAgregarComida(
    private val parentFragment: ComidaActFragment,
    private val openGalleryLauncher: ActivityResultLauncher<Intent>,
    private val onAgregarComida: (Comida) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogComidaBinding
    private var imageUri: Uri? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar nueva comida")
            .setView(binding.root)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }
            .create()

        dialog.setOnShowListener {
            val btnAceptar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            btnAceptar.setOnClickListener {
                val nombre = binding.nombrePlato.text.toString()
                val descripcion = binding.descricion.text.toString()

                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imageUri != null) {
                    val comida = Comida(nombre, descripcion, imageUri.toString())
                    onAgregarComida(comida)
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.galeria.setOnClickListener { openGallery() }

        return dialog
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        openGalleryLauncher.launch(intent)
    }

    fun setImageUri(uri: Uri) {
        imageUri = uri
        binding.imagenPreview.setImageURI(uri)
    }
}
