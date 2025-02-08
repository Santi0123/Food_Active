package com.example.gymactive.ui.views.fragment.VistaGeneral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.data.comida.objects.ComidasData
import com.example.gymactive.databinding.FragmentVistaGeneralBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.ui.views.fragment.VistaGeneral.adapter.AdapterVistaGeneral

class VistaGeneralFragment : Fragment(){

    lateinit var binding: FragmentVistaGeneralBinding
    lateinit var adapterVistaGeneral: AdapterVistaGeneral
    lateinit var listVistaGeneral: List<Comida>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVistaGeneralBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listVistaGeneral = ComidasData.listaComidas.toList()
        binding.rvComidas.layoutManager = LinearLayoutManager(context)
        adapterVistaGeneral = AdapterVistaGeneral(listVistaGeneral)
        binding.rvComidas.adapter = adapterVistaGeneral
    }


}
