package com.example.gymactive.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.gymactive.R
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogEditarComida(
    private val posicion: Int,
    private val comida: Comida,
    private val onActualizarComida: (Comida, Int) -> Unit
) : DialogFragment() {

    private var imageUri: Uri? = null
    private lateinit var binding: DialogComidaBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val viewDialogComida = inflater.inflate(R.layout.dialog_comida, null)
        binding = DialogComidaBinding.bind(viewDialogComida)

        setValuesDialog(binding)

        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(viewDialogComida)
            .setTitle("Vamos a editar")
            .setMessage("Edita tu comida...")
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()

        dialog.setOnShowListener {
            val btnAceptar = dialog.getButton(Dialog.BUTTON_POSITIVE)
            btnAceptar.setOnClickListener {
                val comidaActualizada = recogerDatos(binding)
                if (comidaActualizada.nombre_plato.isEmpty() ||
                    comidaActualizada.descricion.isEmpty() ||
                    comidaActualizada.image.isEmpty()
                ) {
                    Toast.makeText(requireContext(), "Campo vacío", Toast.LENGTH_SHORT).show()
                } else {
                    onActualizarComida(comidaActualizada, posicion)
                    dismiss()
                }
            }
        }

        binding.galeria.setOnClickListener {
            openGallery()
        }

        return dialog
    }

    private fun setValuesDialog(binding: DialogComidaBinding) {
        binding.nombrePlato.setText(comida.nombre_plato)
        binding.descricion.setText(comida.descricion)
        imageUri = Uri.parse(comida.image) // Guardar la URI de la imagen actual
        binding.imagenPreview.setImageURI(imageUri) // Mostrar la imagen actual
    }

    private fun recogerDatos(binding: DialogComidaBinding): Comida {
        return Comida(
            nombre_plato = binding.nombrePlato.text.toString(),
            descricion = binding.descricion.text.toString(),
            image = imageUri.toString() // Recoger la URI de la imagen
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            binding.imagenPreview.setImageURI(imageUri) // Mostrar la imagen seleccionada
        }
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }
}
