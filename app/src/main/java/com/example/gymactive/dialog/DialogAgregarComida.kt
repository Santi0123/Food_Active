package com.example.gymactive.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogAgregarComida(private val onAgregarComida: (Comida) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Agregar nueva comida")
            .setView(binding.root)
            .setPositiveButton("Aceptar") { _, _ ->
                val nombre = binding.nombrePlato.text.toString()
                val descripcion = binding.descricion.text.toString()
                val imagen = binding.imagen.text.toString()

                if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty()) {
                    val comida = Comida(nombre, descripcion, imagen)
                    onAgregarComida(comida)
                } else {
                    Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss() // Cerrar el diálogo
            }
            .create()
    }
}
