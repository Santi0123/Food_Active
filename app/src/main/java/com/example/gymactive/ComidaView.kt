package com.example.gymactive

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.controller.Controller
import com.example.gymactive.databinding.ActivityComidaViewBinding

class ComidaView : AppCompatActivity() {

    private lateinit var controller: Controller
    lateinit var comidaViewBinding: ActivityComidaViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        comidaViewBinding = ActivityComidaViewBinding.inflate(layoutInflater)
        setContentView(comidaViewBinding.root)
        initUI()

    }

    private fun initUI() {
        initRecyclerView()
        controller = Controller(this)
        controller.setAdapter()
    }
    private fun initRecyclerView() {
        comidaViewBinding.rvComida.layoutManager = LinearLayoutManager(this)
    }
}