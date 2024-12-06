package com.example.gymactive.adapter

import android.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.models.Comida

class ViewHolderComida(
    private val binding: ItemComidaBinding,
    val editarComida: (Int) -> Unit,
    val borrarComida: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun render(comida: Comida) {
        // Asignar nombre y descripción
        binding.tvComidaName.text = comida.nombre_plato
        binding.tvComidaDescricion.text = comida.descricion
        // Cargar imagen con Glide
        Glide.with(itemView.context)
            .load(comida.image) // URL de la imagen
            .apply(
                RequestOptions()
                    .centerCrop() // Recorte centrado
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Estrategia de caché
                    .placeholder(R.drawable.progress_indeterminate_horizontal) // Imagen de carga
                    .error(R.drawable.stat_notify_error) // Imagen de error
            )
            .into(binding.ivComidaImage) // ImageView donde se cargará

        // Configurar el botón de eliminación

        binding.btnDelete.setOnClickListener {
            borrarComida(adapterPosition)
        }

        //Configurar el boton de editar
        binding.btnEdit.setOnClickListener{
            editarComida(adapterPosition)
        }
    }
}
