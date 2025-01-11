package com.example.gymactive

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userNameTextView: TextView
    private lateinit var userImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización del Binding para acceder a las vistas definidas en el layout XML
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root) // Establece el layout de la actividad

        // Configuración del título de la barra superior
        supportActionBar?.title = "Gym Active"

        // Inicialización de FirebaseAuth para manejar usuarios
        auth = FirebaseAuth.getInstance()

        // Configuración del Toolbar
        val toolbar = mainBinding.appBarLayoutDrawer.toolbar
        setSupportActionBar(toolbar)

        // Configuración del DrawerLayout y el Navigation Component
        drawerLayout = mainBinding.drawerLayout
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController // Controlador de navegación

        // Configuración de los destinos principales y el DrawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home2), // Destinos principales
            drawerLayout // DrawerLayout asociado
        )

        // Vinculación del AppBar con el controlador de navegación
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Configuración del NavigationView con el controlador de navegación
        mainBinding.navView.setupWithNavController(navController)

        // Carga de datos del usuario para mostrar en el header del Navigation Drawer
        loadUserData(mainBinding.navView)
    }

    // Método para manejar la navegación al presionar "atrás" en la barra superior
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // Método para inflar el menú en la barra de herramientas
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu) // Infla el menú desde el archivo XML
        return true
    }

    // Método para manejar las acciones al seleccionar un elemento del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingMenu -> { // Navegar a la configuración
                navController.navigate(R.id.settingMenu)
                true
            }
            R.id.comidaMenu -> { // Navegar al menú de comida
                navController.navigate(R.id.comidaMenu)
                true
            }
            R.id.logoutMenu -> { // Cerrar sesión
                logout() // Llama a la función para cerrar sesión
                drawerLayout.closeDrawer(GravityCompat.START) // Cierra el Drawer si está abierto
                true
            }
            else -> super.onOptionsItemSelected(item) // Acciones por defecto
        }
    }

    // Función para cerrar sesión del usuario
    private fun logout() {
        auth.signOut() // Cierra la sesión en Firebase
        val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", false) // Marca al usuario como no logueado
        editor.apply() // Guarda los cambios en SharedPreferences

        redirectToLogin() // Redirige al login
    }

    // Función para redirigir al login tras cerrar sesión
    private fun redirectToLogin() {
        val intent = Intent(this, Login::class.java) // Crea una intención para ir a la actividad Login
        startActivity(intent) // Inicia la actividad
        finish() // Finaliza la actividad actual
    }

    // Función para cargar los datos guardados del usuario desde SharedPreferences
    private fun loadUserData(navView: NavigationView) {
        val sharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE) // Accede a SharedPreferences
        val userName = sharedPreferences.getString("userName", "Usuario no encontrado") // Obtiene el nombre del usuario
        val imageUri = sharedPreferences.getString("imageUri", "") // Obtiene la URI de la imagen

        // Accede al header del NavigationView
        val headerView = navView.getHeaderView(0)

        // Inicializa las vistas del header
        userNameTextView = headerView.findViewById(R.id.userNameTextView) // TextView del nombre
        userImageView = headerView.findViewById(R.id.userImageView) // ImageView de la imagen

        // Establece el nombre del usuario en el TextView
        userNameTextView.text = userName

        // Carga la imagen del usuario usando Glide si la URI no está vacía
        if (!imageUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUri)
                .circleCrop() // Hace que la imagen sea circular
                .into(userImageView) // Carga la imagen en el ImageView
        }
    }
}
