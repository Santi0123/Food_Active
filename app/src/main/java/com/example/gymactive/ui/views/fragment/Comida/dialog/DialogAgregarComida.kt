package com.example.gymactive.ui.views.fragment.Comida.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida

class DialogAgregarComida(
    private val comida: (Comida) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Inflamos el layout del diálogo con binding
        val binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))



        // Construcción del diálogo
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar nueva comida")
            .setMessage("Complete los siguientes campos:")
            .setView(binding.root)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()

        // Manejamos el botón "Aceptar" para validar y guardar
        dialog.setOnShowListener {
            val btnAceptar = dialog.getButton(Dialog.BUTTON_POSITIVE)
            btnAceptar.setOnClickListener {
                val nombre = binding.nombrePlato.text.toString()
                val descripcion = binding.descricion.text.toString()
                val imagen = binding.imagen.text.toString()

                // Validamos que los campos no estén vacíos
                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty()) {
                    val nuevaComida = Comida(
                        nombre,
                        descripcion,
                        imagen
                    )

                    comida(nuevaComida)
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "Faltan datos.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }
}
