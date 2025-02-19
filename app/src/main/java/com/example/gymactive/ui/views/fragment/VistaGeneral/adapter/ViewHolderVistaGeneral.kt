package com.example.gymactive.ui.views.fragment.VistaGeneral.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.CardComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida


class ViewHolderVistaGeneral(
    private val binding: CardComidaBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comida: Comida) {
        // Asignar nombre y descripción
        binding.tvComidaName.text = comida.nombre_plato
        binding.tvComidaDescripcion.text = comida.descripcion

        // Cargar imagen con Glide
        val image = comida.image
        Glide.with(itemView.context).let {
            if (image.startsWith("http")) {
                // Cargar imagen desde una URL
                it.load(image)
            } else {
                // Cargar imagen desde un recurso local
                it.load(image.toInt())
            }
        }.centerCrop().into(binding.ivComidaImage)

        // Configurar el botón de eliminación

    }
}