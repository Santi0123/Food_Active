package com.example.gymactive.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.models.Comida


class ViewHolderComida(
    private val binding: ItemComidaBinding,
    private val onDeleteClick: (Comida) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comida: Comida) {
        binding.tvComidaName.text = comida.nombre_plato
        binding.tvComidaDescricion.text = comida.descricion
        // Le ponemos para que se borre cuando pulsemos
        binding.btnDelete.setOnClickListener {
            onDeleteClick(comida)
        }

        Glide.with(itemView.context)
            .load(comida.image)
            .into(binding.ivComidaImage)


    }
}
