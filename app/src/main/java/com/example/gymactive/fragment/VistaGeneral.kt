package com.example.gymactive.fragment

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.R
import com.example.gymactive.adapter.AdapterComidas
import com.example.gymactive.controller.Controller
import com.example.gymactive.dao.DaoComida
import com.example.gymactive.dao.DaoComidas
import com.example.gymactive.databinding.FragmentComidaBinding
import com.example.gymactive.databinding.FragmentVistaGeneralBinding
import com.example.gymactive.models.Comida


class VistaGeneral : Fragment(R.layout.fragment_vista_general) {


    private var binding: FragmentVistaGeneralBinding? = null
    private lateinit var listaComidas: MutableList<Comida>
    private  lateinit var adapterComidas: AdapterComidas

    // Agregar el TextView para anuncios
    private lateinit var adBanner: TextView
    private lateinit var closeAdButton: ImageButton  // Cambiado a ImageButton
    private lateinit var linearLayout2: LinearLayout  // Agregado para el LinearLayout del anuncio

    // Cambiar texto del anuncio cada 5 segundos
    private val adTexts = listOf(
        "Rico rico y con fundamento",
        "Cocinar es fácil si sabes cómo hacerlo",
        "Lo importante no es lo que se hace, sino cómo se hace.",
        "Si cocinas con cariño, todo sabe mejor.",
        "Lo mejor de la cocina es disfrutarla.",
        "Nunca olvides que la cocina es creatividad y amor.",
        "El mejor ingrediente para cualquier receta es la sonrisa.",
        "La cocina es un arte, pero el arte de cocinar es un placer.",
        "Si lo haces con ganas, te saldrá perfecto.",
        "Lo más importante es disfrutar de la comida y compartirla.",
        "En la cocina, todo tiene su truco."
    )
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            adBanner.text = adTexts[currentIndex]
            currentIndex = (currentIndex + 1) % adTexts.size
            handler.postDelayed(this, 5000) // Cambia el texto cada 5 segundos
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVistaGeneralBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        setAdapter()
        initRecyclerView()

        // Inicializa los elementos de la vista
        adBanner = view.findViewById(R.id.adBanner)
        closeAdButton = view.findViewById(R.id.btnCloseAd)  // Inicializa el ImageButton
        linearLayout2 = view.findViewById(R.id.linearLayout2)  // Inicializa el LinearLayout

        // Inicia el ciclo de actualización del texto del anuncio
        handler.post(runnable)

        // Configura el botón para cerrar el anuncio
        closeAdButton.setOnClickListener {
            linearLayout2.visibility = View.GONE  // Oculta todo el LinearLayout, liberando espacio
        }
    }

    private fun initData() {
        listaComidas = DaoComidas.myDao.getDataComida().toMutableList()
    }

    fun setAdapter(){
        adapterComidas = AdapterComidas((listaComidas))
        binding?.rvComidas?.adapter =adapterComidas
    }

    private  fun initRecyclerView(){
        binding?.rvComidas?.layoutManager = LinearLayoutManager(context)
    }
}