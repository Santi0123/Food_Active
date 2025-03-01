package com.example.gymactive.ui.views.fragment.VistaGeneral.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.CardComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ComidaModel


class ViewHolderVistaGeneral(
    private val binding: CardComidaBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comida: ComidaModel) {
        // Asignar nombre y descripción
        binding.tvComidaName.text = comida.nombrePlato
        binding.tvComidaDescripcion.text = comida.descripcion

        // Cargar imagen con Glide
        val imagen = comida.imagen?:""
        Glide.with(itemView.context)
            .let {
                when {
                    imagen.startsWith("http") -> it.load(imagen) // URL remota
                    imagen.startsWith("content://") -> it.load(imagen) // Uri local
                    imagen.length > 100 -> { // Base64 (suelen ser largas)
                        val decodedBytes = Base64.decode(imagen, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        it.load(bitmap)
                    }
                    else -> it.load(imagen.toIntOrNull() ?: 0) // Recurso local
                }
            }
            .centerCrop()
            .into(binding.ivComidaImage)

        // Configurar el botón de eliminación

    }
}