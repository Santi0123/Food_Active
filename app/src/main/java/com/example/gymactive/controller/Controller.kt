package com.example.gymactive.controller

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.ComidaAct
import com.example.gymactive.adapter.AdapterComida
import com.example.gymactive.dao.DaoComida
import com.example.gymactive.dialog.DialogAgregarComida
import com.example.gymactive.dialog.DialogEditarComida
import com.example.gymactive.models.Comida


class Controller(private val context: Context) {
    //private lateinit var adapter: AdapterComida

    private  lateinit var listaComidas : MutableList<Comida>
    private  lateinit var  layoutManager: LinearLayoutManager

    init{
        initData()
        initBotonAgregar()
    }

    fun setAdapter() {
        val comidaActivity = context as ComidaAct
        comidaActivity.activityComidaBinding.rvComida.adapter =
            AdapterComida(listaComidas, ::editarComida, ::borrarComida)
    }

    fun initData(){
        listaComidas = DaoComida.myDao.getDataComida().toMutableList()
    }

    private fun initBotonAgregar() {
        val comidaActivity = context as ComidaAct
        comidaActivity.activityComidaBinding.btnAgregar.setOnClickListener{
            agregarComida()
        }
    }

    fun agregarComida(){
        val dialog = DialogAgregarComida(){ comida -> okNuevaComida(comida) }

        val  comidaActivity = context as ComidaAct
        dialog.show(comidaActivity.supportFragmentManager,"Agragamos comida")

        layoutManager = LinearLayoutManager(context)
        comidaActivity.activityComidaBinding.rvComida.layoutManager = layoutManager
    }

    private fun okNuevaComida(comida: Comida) {
        listaComidas.add(comida)
        val comidaActivity = context as ComidaAct
        comidaActivity.activityComidaBinding.rvComida.adapter?.notifyItemInserted(listaComidas.size - 1)
        layoutManager.scrollToPositionWithOffset(listaComidas.lastIndex, listaComidas.size -1)
    }

    fun editarComida(positionLista: Int) {
        val comidaActivity = context as ComidaAct

        val comidaEditarDialog = DialogEditarComida(positionLista, listaComidas[positionLista]) { comidaActualizada, position ->
            actualizarComida(comidaActualizada, position)
        }

        comidaEditarDialog.show(comidaActivity.supportFragmentManager, "Editamos la comida")
    }

    fun actualizarComida(comidaActualizada: Comida, position: Int) {
        listaComidas[position] = comidaActualizada
        val comidaActivity = context as ComidaAct
        comidaActivity.activityComidaBinding.rvComida.adapter?.notifyItemChanged(position)
    }


    fun borrarComida(position: Int) {
        listaComidas.removeAt(position)
        val comidaActivity = context as ComidaAct
        comidaActivity.activityComidaBinding.rvComida.adapter?.notifyItemRemoved(position)
        comidaActivity.activityComidaBinding.rvComida.adapter?.notifyItemRangeChanged(position, listaComidas.size)
    }

}

