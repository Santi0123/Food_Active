package com.example.gymactive

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.controller.Controller
import com.example.gymactive.databinding.ActivityComidaBinding
import com.example.gymactive.models.Comida

class ComidaAct : AppCompatActivity() {

    lateinit var binding: ActivityComidaBinding
    internal lateinit var controller: Controller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = Controller(this)
        controller.setAdapter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        controller.handleGalleryResult(requestCode, resultCode, data)
    }
}


