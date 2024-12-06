package com.example.gymactive.dialog

import android.app.Dialog
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val viewDialogComida = inflater.inflate(R.layout.dialog_comida, null)
        val binding = DialogComidaBinding.bind(viewDialogComida)

        setValuesDialog(binding)

        return MaterialAlertDialogBuilder(requireActivity())
            .setView(viewDialogComida)
            .setTitle("Vamos a editar")
            .setMessage("Edita tu comida...")
            .setPositiveButton("Aceptar") { _, _ ->
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
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()
    }

    private fun setValuesDialog(binding: DialogComidaBinding) {
        binding.nombrePlato.setText(comida.nombre_plato)
        binding.descricion.setText(comida.descricion)
        binding.imagen.setText(comida.image) // Agregar el campo URL de la imagen
    }

    private fun recogerDatos(binding: DialogComidaBinding): Comida {
        return Comida(
            nombre_plato = binding.nombrePlato.text.toString(),
            descricion = binding.descricion.text.toString(),
            image = binding.imagen.text.toString() // Recoger el campo URL de la imagen
        )
    }
}
