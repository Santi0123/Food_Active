package com.example.gymactive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.models.Comida

class AdapterComida(
    var listaComida: MutableList<Comida>,
    var editarComida: (Int) -> Unit,
    var borrarComida: (Int) -> Unit
) : RecyclerView.Adapter<ViewHolderComida>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComida {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemComidaBinding.inflate(layoutInflater, parent, false)
        return ViewHolderComida(binding, editarComida, borrarComida)
    }

    override fun getItemCount(): Int = listaComida.size

    override fun onBindViewHolder(holder: ViewHolderComida, position: Int) {
        val comida = listaComida[position]
        holder.render(comida)
    }

    /*fun deleteItem(position: Int) {
        if (position in listaComida.indices) {
            listaComida.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listaComida.size)
        }
    }*/
}

