package com.example.gymactive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.models.Comida

class AdapterComida(
    var listComida: MutableList<Comida>,
    private val onDeleteClick: (Comida) -> Unit
) : RecyclerView.Adapter<ViewHolderComida>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComida {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemComidaBinding.inflate(layoutInflater, parent, false)
        // Se la ponemos para que borre cuando se pulse 
        return ViewHolderComida(binding, onDeleteClick)
    }

    override fun getItemCount(): Int = listComida.size

    override fun onBindViewHolder(holder: ViewHolderComida, position: Int) {
        holder.render(listComida[position])
    }
    // Le implementamos la funcion en el adapter
    fun deleteItem(comida: Comida) {
        val position = listComida.indexOf(comida)
        if (position != -1) {
            listComida.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
