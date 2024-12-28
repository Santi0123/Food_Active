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
import com.example.gymactive.dialog.DialogBorrarComida
import com.example.gymactive.dialog.DialogEditarComida
import com.example.gymactive.models.Comida

class Controller(private val context: Context) {

    // Lista mutable que contiene las comidas que se mostrarán en la actividad.
    private lateinit var listaComidas: MutableList<Comida>
    private lateinit var layoutManager: LinearLayoutManager

    // URI de la imagen seleccionada en la galería.
    var imageUri: Uri? = null

    companion object {
        // Constante para identificar la solicitud de selección de imagen en la galería.
        const val PICK_IMAGE_REQUEST = 1
    }

    /**
     * Bloque de inicialización que configura los datos iniciales
     * y el botón para agregar nuevas comidas.
     */
    init {
        initData()
        initBotonAgregar()
    }

    /**
     * Configura el adaptador para el RecyclerView que muestra la lista de comidas.
     */
    fun setAdapter() {
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter =
            AdapterComida(listaComidas, ::editarComida, ::mostrarDialogoBorrarComida)
        layoutManager = LinearLayoutManager(context)
        comidaActivity.binding.rvComida.layoutManager = layoutManager
    }

    /**
     * Inicializa la lista de comidas obteniendo los datos desde el DAO.
     */
    fun initData() {
        listaComidas = DaoComida.myDao.getDataComida().toMutableList()
    }

    /**
     * Configura el botón "Agregar" para abrir el diálogo de agregar comida.
     */
    private fun initBotonAgregar() {
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.btnAgregar.setOnClickListener {
            agregarComida()
        }
    }

    /**
     * Muestra el diálogo de agregar comida, permitiendo al usuario
     * crear una nueva comida e incluirla en la lista.
     */
    fun agregarComida() {
        val dialog = DialogAgregarComida { comida -> okNuevaComida(comida) }
        val comidaActivity = context as ComidaAct
        dialog.show(comidaActivity.supportFragmentManager, "Agragamos comida")
    }

    /**
     * Agrega una nueva comida a la lista y actualiza el RecyclerView.
     *
     * @param comida La comida que se va a agregar.
     */
    private fun okNuevaComida(comida: Comida) {
        listaComidas.add(comida)
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemInserted(listaComidas.size - 1)
        layoutManager.scrollToPositionWithOffset(listaComidas.lastIndex, listaComidas.size - 1)
    }

    /**
     * Muestra el diálogo para editar una comida específica.
     *
     * @param positionLista La posición de la comida en la lista.
     */
    fun editarComida(positionLista: Int) {
        val comidaActivity = context as ComidaAct
        val comidaEditarDialog = DialogEditarComida(positionLista, listaComidas[positionLista]) { comidaActualizada, position ->
            actualizarComida(comidaActualizada, position)
        }
        comidaEditarDialog.show(comidaActivity.supportFragmentManager, "Editamos la comida")
    }

    /**
     * Actualiza los datos de una comida en la lista y notifica al adaptador.
     *
     * @param comidaActualizada La comida con los datos actualizados.
     * @param position La posición de la comida en la lista.
     */
    fun actualizarComida(comidaActualizada: Comida, position: Int) {
        listaComidas[position] = comidaActualizada
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemChanged(position)
    }

    /**
     * Muestra un diálogo de confirmación para eliminar una comida de la lista.
     *
     * @param position La posición de la comida a eliminar.
     */
    private fun mostrarDialogoBorrarComida(position: Int) {
        val comidaActivity = context as ComidaAct
        val comidaBorrarDialog = DialogBorrarComida(position, listaComidas[position]) {
            confirmarBorrado(it)
        }
        comidaBorrarDialog.show(comidaActivity.supportFragmentManager, "Borrar la comida")
    }

    /**
     * Elimina una comida de la lista y actualiza el RecyclerView.
     *
     * @param position La posición de la comida a eliminar.
     */
    fun confirmarBorrado(position: Int) {
        listaComidas.removeAt(position)
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemRemoved(position)
        comidaActivity.binding.rvComida.adapter?.notifyItemRangeChanged(position, listaComidas.size)
    }

    /**
     * Abre la galería del dispositivo para seleccionar una imagen.
     */
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        (context as Activity).startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    /**
     * Procesa el resultado de la selección de imagen en la galería.
     *
     * @param requestCode Código de la solicitud (debe coincidir con PICK_IMAGE_REQUEST).
     * @param resultCode Código de resultado de la actividad.
     * @param data Intent con la información de la imagen seleccionada.
     */
    fun handleGalleryResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
        }
    }
}
