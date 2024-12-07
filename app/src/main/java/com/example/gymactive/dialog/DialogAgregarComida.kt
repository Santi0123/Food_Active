package com.example.gymactive.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.gymactive.ComidaAct
import com.example.gymactive.R
import com.example.gymactive.controller.Controller
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogAgregarComida(private val onAgregarComida: (Comida) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogComidaBinding
    private lateinit var controller: Controller
    private var imageUri: Uri? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))
        controller = (requireActivity() as ComidaAct).controller

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar nueva comida")
            .setView(binding.root)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.setOnShowListener {
            val btnAceptar = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            btnAceptar.setOnClickListener {
                val nombre = binding.nombrePlato.text.toString()
                val descripcion = binding.descricion.text.toString()
                val imagenUri = this@DialogAgregarComida.imageUri

                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagenUri != null) {
                    val comida = Comida(nombre, descripcion, imagenUri.toString())
                    onAgregarComida(comida)
                    dialog.dismiss() // Cerrar el diálogo
                } else {
                    Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.galeria.setOnClickListener {
            openGallery()
        }

        return dialog
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Controller.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.imagenPreview.setImageURI(imageUri)
            controller.imageUri = imageUri
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, Controller.PICK_IMAGE_REQUEST)
    }
}
