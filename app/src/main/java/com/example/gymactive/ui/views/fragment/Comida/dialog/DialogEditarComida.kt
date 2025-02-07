package com.example.gymactive.ui.views.fragment.comida.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogEditarComida(
    private val posicion: Int,  // Posición de la comida a editar
    private val comida: Comida,  // Objeto comida con los datos actuales
    private val onActualizarComida: (Int, Comida) -> Unit  // Callback para actualizar
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Inflamos el layout del diálogo usando ViewBinding
        val binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))

        // Rellenamos los campos del formulario con los datos actuales de la comida
        binding.nombrePlato.setText(comida.nombre_plato ?: "Sin nombre")
        binding.descricion.setText(comida.descricion ?: "Sin descripción")
        binding.imagen.setText(comida.image ?: "Sin imagen")

        // Construcción del diálogo con MaterialAlertDialogBuilder
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)  // Establecemos la vista del diálogo
            .setTitle("Actualizar Comida")  // Título del diálogo
            .setPositiveButton("Aceptar") { _, _ ->
                // Recogemos los datos editados por el usuario

                val nuevoNombre = binding.nombrePlato.text.toString()
                val nuevaDescripcion = binding.descricion.text.toString()
                val nuevaImagen = binding.imagen.text.toString()

                // Creamos una nueva instancia de Comida con los datos actualizados
                val comidaActualizada = Comida(
                    nuevoNombre,
                    nuevaDescripcion,  // Corregimos la propiedad de descripción
                    nuevaImagen  // Usamos la imagen correctamente
                )

                // Llamamos al callback para actualizar la comida en la lista
                onActualizarComida(posicion, comidaActualizada)
                dismiss()  // Cerramos el diálogo
            }
            .setNegativeButton("Cancelar") { _, _ ->
                dismiss()  // Cierra el diálogo sin hacer cambios
            }
            .create()  // Crea el diálogo

        return dialog  // Devuelve el diálogo creado
    }
}
