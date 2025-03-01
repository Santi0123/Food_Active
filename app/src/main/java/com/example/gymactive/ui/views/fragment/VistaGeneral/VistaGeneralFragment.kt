package com.example.gymactive.ui.views.fragment.VistaGeneral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.data.comida.objects.ComidasData
import com.example.gymactive.databinding.FragmentVistaGeneralBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.ui.viewmodel.vistageneral.VistaGeneralViewModel
import com.example.gymactive.ui.views.fragment.VistaGeneral.adapter.AdapterVistaGeneral
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VistaGeneralFragment : Fragment(){

    lateinit var binding: FragmentVistaGeneralBinding
    val vistaGeneralViewModel: VistaGeneralViewModel by viewModels()
    lateinit var adapterVistaGeneral: AdapterVistaGeneral

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
        binding.rvComidas.layoutManager = LinearLayoutManager(activity)
        setAdapter()
        setObserver()
        vistaGeneralViewModel.getAllComidas()
    }

    private fun setAdapter(){
        adapterVistaGeneral = AdapterVistaGeneral(
            listaComidas = emptyList()
        )
        this.binding.rvComidas.adapter = adapterVistaGeneral
    }

    private fun setObserver(){
        vistaGeneralViewModel.allComidasLiveData.observe(viewLifecycleOwner, {comidas->
            adapterVistaGeneral.listaComidas = comidas ?: emptyList()
            adapterVistaGeneral.notifyDataSetChanged()
        })
    }


}
