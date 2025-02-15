package com.example.gymactive.ui.views.fragment.Comida.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida

class ViewHolderComida(
    private val binding: ItemComidaBinding,
    val editarComida: (Int) -> Unit,
    val borrarComida: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comida: Comida) {
        // Asignar nombre y descripción
        binding.tvComidaName.text = comida.nombre_plato
        binding.tvComidaDescripcion.text = comida.descricion

        val image = comida.image
        Glide.with(itemView.context)
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
            .into(binding.ivComidaImage)

        // Configurar el botón de eliminación
        binding.btnDelete.setOnClickListener {
            borrarComida(adapterPosition)
        }

        // Configurar el botón de editar
        binding.btnEdit.setOnClickListener {
            editarComida(adapterPosition)
        }
    }
}
