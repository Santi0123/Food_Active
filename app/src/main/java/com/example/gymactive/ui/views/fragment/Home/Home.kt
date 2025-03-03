package com.example.gymactive.ui.views.fragment.Home

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.cardview.widget.CardView
import com.example.gymactive.R

class Home : Fragment() {

    private lateinit var videoView: VideoView
    private lateinit var btnOmitir: Button
    private lateinit var titleTextView: TextView

    private val PREFS_NAME = "session_prefs"
    private val KEY_VIDEO_SEEN = "video_seen_"
    private val KEY_IS_LOGGED_IN = "is_logged_in"
    private val KEY_USER_ID = "userId"
    private val KEY_USER_NAME = "nombre" // Clave para el nombre del usuario

    private lateinit var shared: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        videoView = view.findViewById(R.id.videoView)
        btnOmitir = view.findViewById(R.id.btnOmitir)
        titleTextView = view.findViewById(R.id.titleTextView)

        // Inicializar SharedPreferences
        shared = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Verificar si el usuario está logueado
        val isLoggedIn = shared.getBoolean(KEY_IS_LOGGED_IN, false)

        if (isLoggedIn) {
            // Obtener el nombre del usuario logueado
            val userName = shared.getString(KEY_USER_NAME, "Aún no tiene nombre") ?: "Aún no tiene nombre"
            // Mostrar el nombre del usuario en el TextView
            titleTextView.text = userName

            // Obtener el ID del usuario logueado
            val userId = shared.getInt(KEY_USER_ID, -1)

            if (userId != -1) {
                val videoSeen = shared.getBoolean("$KEY_VIDEO_SEEN$userId", false)

                if (!videoSeen) {
                    // Si el usuario no ha visto el video, mostrarlo
                    playVideo()
                } else {
                    // Si ya ha visto el video, ocultarlo
                    videoView.visibility = View.GONE
                    btnOmitir.visibility = View.GONE
                }
            }
        } else {
            // Si no está logueado, no hacemos nada con el video
            videoView.visibility = View.GONE
            btnOmitir.visibility = View.GONE
        }

        btnOmitir.setOnClickListener {
            // Guardamos que el video ha sido visto para el usuario logueado
            val userId = shared.getInt(KEY_USER_ID, -1)
            if (userId != -1) {
                shared.edit().putBoolean("$KEY_VIDEO_SEEN$userId", true).apply()
            }
            videoView.visibility = View.GONE
            btnOmitir.visibility = View.GONE
        }

        return view
    }

    private fun playVideo() {
        val videoPath = "android.resource://${requireActivity().packageName}/${R.raw.video_comida}"
        videoView.setVideoURI(Uri.parse(videoPath))
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = false
            videoView.start()
        }
    }

    // Método para resetear la preferencia de si se ha visto el video para el usuario logueado
    fun resetVideoPreference() {
        val userId = shared.getInt(KEY_USER_ID, -1)
        if (userId != -1) {
            shared.edit().putBoolean("$KEY_VIDEO_SEEN$userId", false).apply()
        }
    }
}
