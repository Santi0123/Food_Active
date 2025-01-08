package com.example.gymactive

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gymactive.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var buttonDrawerToggle: ImageButton
    private lateinit var navView: NavigationView

    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var userImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Inicialización principal de la UI
        initUI()

        // Cargar los datos guardados de SharedPreferences en el menú
        loadUserData()
    }

    // Inicializar la interfaz de usuario
    private fun initUI() {
        setupFirebase()
        setupDrawer()
        checkUserSession()
        setupListeners()
    }

    // Configuración de Firebase
    private fun setupFirebase() {
        auth = FirebaseAuth.getInstance()
    }

    // Configuración del DrawerLayout
    private fun setupDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggle)
        navView = findViewById(R.id.nav_view)

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        buttonDrawerToggle.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Listener para los items del menú
        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.comida_menu -> {
                    val intent = Intent(this, ComidaAct::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.setting_menu -> {
                    val intent = Intent(this, Setting::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.logout_menu -> {
                    logout()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }

                else -> false
            }
        }
    }

    // Verificar sesión del usuario
    private fun checkUserSession() {
        val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

        val currentUser = auth.currentUser
        if (currentUser != null && currentUser.isEmailVerified && isLoggedIn) {
            enableUserFeatures()
        } else {
            redirectToLogin()
        }
    }

    // Configuración de listeners
    private fun setupListeners() {
        mainBinding.buttonRecyclerView.setOnClickListener { irComida() }
        mainBinding.buttonLogin.setOnClickListener { logout() }
    }

    // Función para redirigir al login
    private fun redirectToLogin() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    // Habilitar funciones para usuarios autenticados
    private fun enableUserFeatures() {
        // Implementar aquí las funciones habilitadas para el usuario autenticado
    }

    // Función para cerrar sesión
    private fun logout() {
        auth.signOut()
        val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", false)
        editor.apply()

        redirectToLogin()
    }

    // Redirigir a la actividad de comida
    private fun irComida() {
        val intent = Intent(this, ComidaAct::class.java)
        startActivity(intent)
    }

    // Cargar datos guardados desde SharedPreferences en el menú
    private fun loadUserData() {
        // Obtener los valores desde SharedPreferences
        val sharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "Usuario no encontrado")
        val userEmail = sharedPreferences.getString("userEmail", "email@ejemplo.com")
        val imageUri = sharedPreferences.getString("imageUri", "")

        // Acceder al header del NavigationView
        val headerView = navView.getHeaderView(0)

        // Inicializar las vistas del header
        userNameTextView = headerView.findViewById(R.id.userNameTextView)
        userImageView = headerView.findViewById(R.id.userImageView)

        // Establecer el nombre y el correo
        userNameTextView.text = userName

        // Cargar la imagen desde la URI, si existe
        if (!imageUri.isNullOrEmpty()) {
            // Usando Glide para cargar la imagen
            Glide.with(this)
                .load(imageUri)
                .circleCrop()  // Esto es opcional si deseas que la imagen sea circular
                .into(userImageView)
        }
    }
}
