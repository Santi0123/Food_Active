package com.example.gymactive.ui.views.fragment.VistaGeneral.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymactive.databinding.CardComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ComidaModel


class AdapterVistaGeneral(
    var listaComidas: List<ComidaModel>,

    ) : RecyclerView.Adapter<ViewHolderVistaGeneral>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderVistaGeneral {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardComidaBinding.inflate(layoutInflater, parent, false)
        return ViewHolderVistaGeneral(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderVistaGeneral, position: Int) {
        holder.render(listaComidas[position])
    }

    override fun getItemCount(): Int {
        return listaComidas.size
    }
}