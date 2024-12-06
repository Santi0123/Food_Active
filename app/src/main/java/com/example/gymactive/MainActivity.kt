package com.example.gymactive


import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.gymactive.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initUI()
    }

    private fun initUI() {
        setOnClick()
        mainBinding.buttonRecyclerView.setOnClickListener{irComida()}
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

    private fun irComida(){
        val intent = Intent(this, ComidaAct::class.java)
        startActivity(intent)
    }
}
