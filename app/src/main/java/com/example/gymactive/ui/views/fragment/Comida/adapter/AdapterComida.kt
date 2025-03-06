package com.example.gymactive.ui.views.fragment.Comida.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymactive.databinding.ItemComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ComidaModel

class AdapterComida(
    val context: Context,
    var listaComidas: MutableList<ComidaModel>,
    var editarComida: (ComidaModel) -> Unit,
    var borrarComida: (ComidaModel) -> Unit,
) : RecyclerView.Adapter<ViewHolderComida>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComida {
        val binding = ItemComidaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderComida(context, binding, editarComida, borrarComida)
    }

    override fun onBindViewHolder(holder: ViewHolderComida, position: Int) {
        holder.render(listaComidas[position])
    }

    override fun getItemCount(): Int {
        return listaComidas.size
    }
}
