package com.example.gymactive.dialog

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.gymactive.R
import com.example.gymactive.databinding.BorrarComidaDialogBinding
import com.example.gymactive.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogBorrarComida(
    private val posicion: Int,
    private val comida: Comida,
    private val onBorrarComida: (Int) -> Unit
) : DialogFragment() {

    private var _binding: BorrarComidaDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BorrarComidaDialogBinding.inflate(LayoutInflater.from(requireContext()))

        setValuesDialog(binding)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirmar Borrado")
            .setView(binding.root)
            .setMessage("¿Estás seguro de que deseas borrar esta comida?")
            .setPositiveButton("Borrar", null)
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()

        dialog.setOnShowListener {
            val btnBorrar = dialog.getButton(Dialog.BUTTON_POSITIVE)
            btnBorrar.setOnClickListener {
                onBorrarComida(posicion)
                dismiss()
            }
        }

        return dialog
    }

    private fun setValuesDialog(binding: BorrarComidaDialogBinding) {
        binding.nombrePlato.setText(comida.nombre_plato)
        binding.descricion.setText(comida.descricion)

        val imageUri = Uri.parse(comida.image)
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
