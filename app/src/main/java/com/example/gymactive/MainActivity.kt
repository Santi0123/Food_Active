package com.example.gymactive

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        auth = FirebaseAuth.getInstance()

        // Verifica si hay una sesión iniciada en SharedPreferences
        val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        // Verifica si el usuario está autenticado
        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified && isLoggedIn) {
            // Usuario autenticado y sesión iniciada, inicia la UI principal
            initUI()
        } else {
            // No hay usuario autenticado o sesión no iniciada, redirige a LoginActivity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initUI() {
        setOnClick()
        mainBinding.buttonRecyclerView.setOnClickListener { irComida() }
        mainBinding.buttonLogin.setOnClickListener{ logout()}
    }

    private fun logout() {
        // Cerrar sesión en Firebase
        auth.signOut()

        // Actualizar SharedPreferences
        val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", false)
        editor.apply()

        // Redirigir a LoginActivity
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }


    private fun setOnClick() {
        mainBinding.botonFab.setOnClickListener {
            texto()
        }
    }

    private fun texto() {
        val view = mainBinding.botonFab

        Snackbar.make(view, "A por todas!!!", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun irComida() {
        val intent = Intent(this, ComidaAct::class.java)
        startActivity(intent)
    }
}
