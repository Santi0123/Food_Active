package com.example.gymactive.ui.views.fragment.VistaGeneral.adapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.gymactive.databinding.CardComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ComidaModel


class ViewHolderVistaGeneral(
    val context: Context,
    private val binding: CardComidaBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var shared: SharedPreferences

    fun render(comida: ComidaModel) {
        // Asignar nombre y descripción
        shared = context.getSharedPreferences("session_prefs",Context.MODE_PRIVATE)
        binding.tvComidaName.text = comida.nombrePlato
        binding.tvComidaDescripcion.text = comida.descripcion

        val ip = "http://192.168.1.146:8081/images"
        val userId = shared.getInt("userId",-1)
        val image = comida.imagen
        val url = "$ip/$userId/$image"

        val token = "Bearer " + shared.getString("token",null)

        if(image != null){
            Glide.with(itemView.context)
                .let {
                    when {
                        image.startsWith("http") -> it.load(image) // URL remota
                        image.startsWith("content://") -> it.load(image) // Uri local
                        image.length > 100 -> { // Base64 (suelen ser largas)
                            val decodedBytes = Base64.decode(image, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                            it.load(bitmap)
                        }
                        else -> {
                            it.load(
                                GlideUrl(
                                    url, LazyHeaders.Builder()
                                        .addHeader("Authorization",token)
                                        .build()
                                )
                            )
                        }
                    }
                }
                .centerCrop()
                .into(binding.ivComidaImage)
        }


        // Configurar el botón de eliminación

    }
}