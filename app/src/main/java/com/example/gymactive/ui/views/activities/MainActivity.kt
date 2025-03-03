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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userImageView: ImageView
    private lateinit var userNameTextView: TextView
    private lateinit var userEmailTextView: TextView
    private lateinit var bottomAppBar: BottomAppBar
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        supportActionBar?.title = "Gym Active"
        val toolbar = mainBinding.appBarLayoutDrawer.toolbar
        setSupportActionBar(toolbar)

        drawerLayout = mainBinding.drawerLayout
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController
        appBarConfiguration = AppBarConfiguration(setOf(R.id.home2), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        mainBinding.navView.setupWithNavController(navController)

        bottomAppBar = findViewById(R.id.my_bottom_app_bar)
        bottomNavigationView = findViewById(R.id.my_bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
        bottomAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        mainBinding.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.vistaGeneral -> {
                    navController.navigate(R.id.vistaGeneral)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.settingMenu -> {
                    navController.navigate(R.id.settingMenu)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.comidaMenu -> {
                    navController.navigate(R.id.comidaMenu)
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.logoutMenu -> {
                    logout()
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                else -> drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }

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
                R.id.vistaGeneral -> {
                    navController.navigate(R.id.vistaGeneral)
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home2 -> bottomNavigationView.menu.findItem(R.id.home2).isChecked = true
                R.id.settingMenu -> bottomNavigationView.menu.findItem(R.id.settingMenu).isChecked = true
                R.id.comidaMenu -> bottomNavigationView.menu.findItem(R.id.comidaMenu).isChecked = true
            }
        }

        loadUserData(mainBinding.navView)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingMenu -> {
                navController.navigate(R.id.settingMenu)
                true
            }
            R.id.comidaMenu -> {
                navController.navigate(R.id.comidaMenu)
                true
            }
            R.id.logoutMenu -> {
                logout()
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        getSharedPreferences("session_prefs", MODE_PRIVATE)
            .edit()
            .putInt("userId", -1)
            .putString("email", "")
            .putString("nombre", "")
            .putString("imagen","")
            .putBoolean("is_logged_in", false)
            .apply()
        navController.popBackStack(R.id.vistaGeneral, false)
        navController.navigate(R.id.vistaGeneral)
    }

    private fun loadUserData(navView: NavigationView) {
        val sharedPreferences = getSharedPreferences("session_prefs", MODE_PRIVATE)
        val userName = sharedPreferences.getString("nombre", "Nombre de Usuario")
        val userEmail = sharedPreferences.getString("email", "user@example.com")
        val imageUriString = sharedPreferences.getString("imagen", "")

        val headerView = navView.getHeaderView(0)
        userImageView = headerView.findViewById(R.id.userImageView)
        userNameTextView = headerView.findViewById(R.id.userNameTextView)
        userEmailTextView = headerView.findViewById(R.id.userEmailTextView)

        userNameTextView.text = userName
        userEmailTextView.text = userEmail

        if (!imageUriString.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUriString)
                .circleCrop()
                .into(userImageView)
        }
    }

}
