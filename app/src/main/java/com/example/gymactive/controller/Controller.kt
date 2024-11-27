package com.example.gymactive.controller

import android.content.Context
import android.widget.Toast
import com.example.gymactive.ComidaView
import com.example.gymactive.adapter.AdapterComida
import com.example.gymactive.dao.DaoComida
import com.example.gymactive.models.Comida

class Controller(private val activity: ComidaView) {

    private lateinit var adapter: AdapterComida

    fun setAdapter() {
        val listaComidas = DaoComida.myDao.getDataComida().toMutableList()
        //actualizamos el adapter para que se borre
        adapter = AdapterComida(listaComidas) { comida ->
            DaoComida.myDao.deleteComida(comida)
            adapter.deleteItem(comida)
        }

        activity.comidaViewBinding.rvComida.adapter = adapter
    }
}

