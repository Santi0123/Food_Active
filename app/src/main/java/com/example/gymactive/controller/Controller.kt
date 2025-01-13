package com.example.gymactive.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.adapter.AdapterComida
import com.example.gymactive.dao.DaoComida
import com.example.gymactive.dialog.DialogAgregarComida
import com.example.gymactive.dialog.DialogBorrarComida
import com.example.gymactive.dialog.DialogEditarComida
import com.example.gymactive.models.Comida
import com.example.gymactive.databinding.FragmentComidaBinding
import com.example.gymactive.fragment.ComidaActFragment

class Controller(private val context: Context, private val openGalleryLauncher: ActivityResultLauncher<Intent>) {

    private lateinit var listaComidas: MutableList<Comida>
    private lateinit var layoutManager: LinearLayoutManager
    var imageUri: Uri? = null
    var dialogAgregarComida: DialogAgregarComida? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    init {
        initData()
    }


    fun setAdapter(binding: FragmentComidaBinding) {
        layoutManager = LinearLayoutManager(context)
        binding.rvComida.layoutManager = layoutManager
        binding.rvComida.adapter = AdapterComida(
            listaComidas = listaComidas,
            editarComida = { position, binding -> editarComida(position, binding) },
            borrarComida = { position, binding -> mostrarDialogoBorrarComida(position, binding) },
            fragmentBinding = binding
        )
    }

    private fun initData() {
        listaComidas = DaoComida.myDao.getDataComida().toMutableList()
    }

    fun initBotonAgregar(binding: FragmentComidaBinding, fragment: ComidaActFragment) {
        binding.btnAgregar.setOnClickListener {
            agregarComida(binding, fragment)
        }
    }

    private fun agregarComida(binding: FragmentComidaBinding, fragment: ComidaActFragment) {
        dialogAgregarComida = DialogAgregarComida(fragment, openGalleryLauncher) { comida ->
            okNuevaComida(comida, binding)
            dialogAgregarComida = null
        }
        if (context is AppCompatActivity) {
            val activity = context as AppCompatActivity
            dialogAgregarComida?.show(activity.supportFragmentManager, "Agregar comida")
            Log.d("Controller", "Dialog should be shown now")
        } else {
            throw IllegalStateException("Context must be an instance of AppCompatActivity")
        }
    }

    private fun okNuevaComida(comida: Comida, binding: FragmentComidaBinding) {
        listaComidas.add(comida)
        binding.rvComida.adapter?.notifyItemInserted(listaComidas.size - 1)
        layoutManager.scrollToPosition(listaComidas.size - 1)
    }

    private fun editarComida(positionLista: Int, binding: FragmentComidaBinding) {
        val dialog = DialogEditarComida(positionLista, listaComidas[positionLista]) { comidaActualizada, position ->
            actualizarComida(comidaActualizada, position, binding)
        }
        if (context is AppCompatActivity) {
            val activity = context as AppCompatActivity
            dialog.show(activity.supportFragmentManager, "Editar comida")
        } else {
            throw IllegalStateException("Context must be an instance of AppCompatActivity")
        }
    }

    private fun actualizarComida(comidaActualizada: Comida, position: Int, binding: FragmentComidaBinding) {
        listaComidas[position] = comidaActualizada
        binding.rvComida.adapter?.notifyItemChanged(position)
    }

    private fun mostrarDialogoBorrarComida(position: Int, binding: FragmentComidaBinding) {
        val dialog = DialogBorrarComida(position, listaComidas[position]) {
            confirmarBorrado(it, binding)
        }
        if (context is AppCompatActivity) {
            val activity = context as AppCompatActivity
            dialog.show(activity.supportFragmentManager, "Borrar comida")
        } else {
            throw IllegalStateException("Context must be an instance of AppCompatActivity")
        }
    }

    private fun confirmarBorrado(position: Int, binding: FragmentComidaBinding) {
        listaComidas.removeAt(position)
        binding.rvComida.adapter?.notifyItemRemoved(position)
        binding.rvComida.adapter?.notifyItemRangeChanged(position, listaComidas.size)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        openGalleryLauncher.launch(intent)
    }

    fun handleGalleryResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
        }
    }
}
