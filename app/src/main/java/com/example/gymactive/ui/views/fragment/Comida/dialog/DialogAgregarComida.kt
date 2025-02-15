package com.example.gymactive.ui.views.fragment.comida.dialog

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream

class DialogAgregarComida(
    private val comida: (Comida) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogComidaBinding
    private var imagenBase64: String? = null

    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.imagenPreview.setImageURI(it)
            convertirImagenABase64(it)
        }
    }

    private val tomarFoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            binding.imagenPreview.setImageBitmap(it)
            imagenBase64 = convertirBitmapABase64(it)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))

        binding.imagenPreview.setOnClickListener { mostrarOpcionesImagen() }

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar nueva comida")
            .setMessage("Complete los siguientes campos:")
            .setView(binding.root)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()

        dialog.setOnShowListener {
            val btnAceptar = dialog.getButton(Dialog.BUTTON_POSITIVE)
            btnAceptar.setOnClickListener {
                val nombre = binding.nombrePlato.text.toString()
                val descripcion = binding.descricion.text.toString()

                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && !imagenBase64.isNullOrEmpty()) {
                    val nuevaComida = Comida(nombre, descripcion, imagenBase64!!)
                    comida(nuevaComida)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Faltan datos.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }

    private fun mostrarOpcionesImagen() {
        val opciones = arrayOf("Tomar foto", "Seleccionar de galería")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Elige una opción")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> tomarFoto.launch(null)
                    1 -> seleccionarImagen.launch("image/*")
                }
            }
            .show()
    }

    private fun convertirImagenABase64(uri: android.net.Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imagenBase64 = convertirBitmapABase64(bitmap)
    }

    private fun convertirBitmapABase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}
