package com.example.gymactive

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.controller.Controller
import com.example.gymactive.databinding.ActivityComidaBinding
import com.example.gymactive.models.Comida

class ComidaAct : AppCompatActivity() {

    private lateinit var controller: Controller
    lateinit var activityComidaBinding: ActivityComidaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        activityComidaBinding = ActivityComidaBinding.inflate(layoutInflater)
        setContentView(activityComidaBinding.root)
        init()
    }

    private fun init() {
        initRecyclerView()
        controller = Controller(this)
        controller.setAdapter()

    }

    private fun initRecyclerView() {
        activityComidaBinding.rvComida.layoutManager = LinearLayoutManager(this)
    }

    fun mostrarDialogoEditar(comidaEditar: Comida, position: Int) {

    }
}