package com.example.gymactive.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.ComidaAct
import com.example.gymactive.adapter.AdapterComida
import com.example.gymactive.dao.DaoComida
import com.example.gymactive.dialog.DialogAgregarComida
import com.example.gymactive.dialog.DialogEditarComida
import com.example.gymactive.models.Comida

class Controller(private val context: Context) {

    private lateinit var listaComidas: MutableList<Comida>
    private lateinit var layoutManager: LinearLayoutManager
    var imageUri: Uri? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    init {
        initData()
        initBotonAgregar()
    }

    fun setAdapter() {
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter =
            AdapterComida(listaComidas, ::editarComida, ::borrarComida)
        layoutManager = LinearLayoutManager(context)
        comidaActivity.binding.rvComida.layoutManager = layoutManager
    }

    fun initData() {
        listaComidas = DaoComida.myDao.getDataComida().toMutableList()
    }

    private fun initBotonAgregar() {
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.btnAgregar.setOnClickListener {
            agregarComida()
        }
    }

    fun agregarComida() {
        val dialog = DialogAgregarComida { comida -> okNuevaComida(comida) }

        val comidaActivity = context as ComidaAct
        dialog.show(comidaActivity.supportFragmentManager, "Agragamos comida")
    }

    private fun okNuevaComida(comida: Comida) {
        listaComidas.add(comida)
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemInserted(listaComidas.size - 1)
        layoutManager.scrollToPositionWithOffset(listaComidas.lastIndex, listaComidas.size - 1)
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
        comidaActivity.binding.rvComida.adapter?.notifyItemChanged(position)
    }

    fun borrarComida(position: Int) {
        listaComidas.removeAt(position)
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemRemoved(position)
        comidaActivity.binding.rvComida.adapter?.notifyItemRangeChanged(position, listaComidas.size)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        (context as Activity).startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    fun handleGalleryResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            // Aquí puedes manejar la imageUri según sea necesario
        }
    }
}
