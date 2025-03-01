package com.example.gymactive.ui.views.fragment.Comida.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.gymactive.R
import com.example.gymactive.databinding.BorrarComidaDialogBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ComidaModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogBorrarComida(
    private val comida: ComidaModel,  // La comida que se desea eliminar
    private val onBorrarComida: (ComidaModel) -> Unit  // Callback para eliminar la comida
) : DialogFragment() {

    private var _binding: BorrarComidaDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BorrarComidaDialogBinding.inflate(LayoutInflater.from(requireContext()))

        setValuesDialog(binding)

        // Creación del diálogo con MaterialAlertDialog
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.CustomDialogStyle)
            .setView(binding.root)
            .setMessage("¿Seguro que quieres eliminar la comida \"${comida.nombrePlato}\"?")
            .setPositiveButton("Eliminar") { _, _ ->
                // Llamamos al callback para eliminar la comida
                onBorrarComida(comida)
                dismiss()
            }
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()
            dialog.setOnShowListener {
                val btnAceptar = dialog.getButton(Dialog.BUTTON_POSITIVE)
                val btnCancelar = dialog.getButton(Dialog.BUTTON_NEGATIVE)

                // Estilo para los botones
                btnAceptar.apply {
                    setBackgroundResource(R.drawable.rounded_button)  // Fondo redondeado para Aceptar
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )  // Color blanco para el texto
                }

                btnCancelar.apply {
                    setBackgroundResource(R.drawable.rounded_button)  // Fondo redondeado para Cancelar
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )  // Color blanco para el texto
                }
            }

        return dialog
    }

    private fun setValuesDialog(binding: BorrarComidaDialogBinding) {
        // Establecer los valores de los campos
        binding.nombrePlato.apply {
            setText(comida.nombrePlato ?: "Sin nombre")
            isEnabled = false  // Deshabilitar edición
        }
        binding.descricion.apply {
            setText(comida.descripcion ?: "Sin descripción")
            isEnabled = false  // Deshabilitar edición
        }

        comida.imagen?.let { loadImage(it) }

    }

    private fun loadImage(image:String) {
        Glide.with(this)
            .let {
                when {
                    image.startsWith("http") -> it.load(image) // URL remota
                    image.startsWith("content://") -> it.load(image) // Uri local
                    image.length > 100 -> { // Base64 (suelen ser largas)
                        val decodedBytes = Base64.decode(image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        it.load(bitmap)
                    }
                    else -> it.load(image.toIntOrNull() ?: 0) // Recurso local
                }
            }
            .centerCrop()
            .into(binding.imagenPreview)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
