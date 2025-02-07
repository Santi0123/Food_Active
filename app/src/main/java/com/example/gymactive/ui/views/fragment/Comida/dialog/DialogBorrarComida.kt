package com.example.gymactive.ui.views.fragment.Comida.dialog

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.gymactive.R
import com.example.gymactive.databinding.BorrarComidaDialogBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogBorrarComida(
    private val posicion: Int,  // Posición de la comida a eliminar
    private val comida: Comida,  // La comida que se desea eliminar
    private val onBorrarComida: (Int) -> Unit  // Callback para eliminar la comida
) : DialogFragment() {

    private var _binding: BorrarComidaDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BorrarComidaDialogBinding.inflate(LayoutInflater.from(requireContext()))

        setValuesDialog(binding)

        // Creación del diálogo con MaterialAlertDialog
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar comida")
            .setView(binding.root)
            .setMessage("¿Seguro que quieres eliminar la comida \"${comida.nombre_plato}\"?")
            .setPositiveButton("Eliminar") { _, _ ->
                // Llamamos al callback para eliminar la comida
                onBorrarComida(posicion)
                dismiss()
            }
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()

        return dialog
    }

    private fun setValuesDialog(binding: BorrarComidaDialogBinding) {
        // Establecer los valores de los campos
        binding.nombrePlato.apply {
            setText(comida.nombre_plato ?: "Sin nombre")
            isEnabled = false  // Deshabilitar edición
        }
        binding.descricion.apply {
            setText(comida.descricion ?: "Sin descripción")
            isEnabled = false  // Deshabilitar edición
        }

        // Establecer la imagen si existe
        val imageUri = comida.image?.let { Uri.parse(it) }
        if (!imageUri.toString().isNullOrEmpty()) {
            binding.imagenPreview.setImageURI(imageUri)
        } else {
            binding.imagenPreview.setImageResource(R.drawable.galeria)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
