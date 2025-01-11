package com.example.gymactive.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymactive.databinding.FragmentComidaBinding
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.models.Comida

class AdapterComida(
    private val listaComidas: MutableList<Comida>,
    private val editarComida: (Int, FragmentComidaBinding) -> Unit,
    private val borrarComida: (Int, FragmentComidaBinding) -> Unit,
    private val fragmentBinding: FragmentComidaBinding
) : RecyclerView.Adapter<ViewHolderComida>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComida {
        val binding = ItemComidaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderComida(binding, fragmentBinding, editarComida, borrarComida)
    }

    override fun onBindViewHolder(holder: ViewHolderComida, position: Int) {
        holder.render(listaComidas[position])
    }

    override fun getItemCount(): Int {
        return listaComidas.size
    }
}
