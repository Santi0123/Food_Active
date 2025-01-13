package com.example.gymactive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymactive.databinding.CardComidaBinding
import com.example.gymactive.models.Comida

class AdapterComidas(
    private val listaComidas: MutableList<Comida>,

) : RecyclerView.Adapter<ViewHolderComidas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComidas {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardComidaBinding.inflate(layoutInflater, parent, false)
        return ViewHolderComidas(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderComidas, position: Int) {
        holder.render(listaComidas[position])
    }

    override fun getItemCount(): Int {
        return listaComidas.size
    }
}