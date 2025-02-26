package com.example.gymactive.ui.views.activities

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
import com.example.gymactive.R
import com.example.gymactive.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userNameTextView: TextView
    private lateinit var userImageView: ImageView
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización del Binding para acceder a las vistas definidas en el layout XML
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root) // Establece el layout de la actividad


        // Configuración del título de la barra superior
        supportActionBar?.title = "Gym Active"

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

        // Inicialización del BottomAppBar y el BottomNavigationView
        bottomAppBar = findViewById(R.id.my_bottom_app_bar)
        bottomNavigationView = findViewById(R.id.my_bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Configuración del BottomAppBar
        bottomAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        mainBinding.navView.setNavigationItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.vistaGeneral ->{
                        navController.navigate(R.id.vistaGeneral)
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.settingMenu -> { // Navegar a la configuración
                        navController.navigate(R.id.settingMenu)
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.comidaMenu -> { // Navegar al menú de comida
                        navController.navigate(R.id.comidaMenu)
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.logoutMenu -> {
                        logout()
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    else ->  drawerLayout.closeDrawer(GravityCompat.START)
                }
            true
        }

        // Configuración del BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settingMenu -> {
                    navController.navigate(R.id.settingMenu)
                    true
                }
                R.id.comidaMenu -> {
                    navController.navigate(R.id.comidaMenu)
                    true
                }
                R.id.home2 -> {
                    navController.navigate(R.id.home2)
                    true
                }
                R.id.vistaGeneral ->{
                    navController.navigate(R.id.vistaGeneral)
                    true
                }

                else -> false
            }
        }

        // Listener para actualizar el BottomNavigationView cuando cambie el destino
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home2 -> bottomNavigationView.menu.findItem(R.id.home2).isChecked = true
                R.id.settingMenu -> bottomNavigationView.menu.findItem(R.id.settingMenu).isChecked = true
                R.id.comidaMenu -> bottomNavigationView.menu.findItem(R.id.comidaMenu).isChecked = true
            }
        }

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

    private fun logout() {
        getSharedPreferences("session_prefs", MODE_PRIVATE)
            .edit()
            .putString("email","")
            .putString("nombre","")
            .putBoolean("is_logged_in",false)
            .apply()
        navController.popBackStack(R.id.vistaGeneral,false)
        navController.navigate(R.id.vistaGeneral)
    }

    private fun loadUserData(navView: NavigationView) {
        val sharedPreferences = getSharedPreferences("UserSettings", MODE_PRIVATE)
        val imageUri = sharedPreferences.getString("imageUri", "")

        val headerView = navView.getHeaderView(0)
        userImageView = headerView.findViewById(R.id.userImageView)

        if (!imageUri.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUri)
                .circleCrop()  // Opcional: para mostrar la imagen como un círculo
                .into(userImageView)
        }
    }

}
