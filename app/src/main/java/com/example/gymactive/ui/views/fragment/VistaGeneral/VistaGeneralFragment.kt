package com.example.gymactive.ui.views.fragment.VistaGeneral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.databinding.FragmentVistaGeneralBinding
import com.example.gymactive.ui.viewmodel.vistageneral.VistaGeneralViewModel
import com.example.gymactive.ui.views.fragment.VistaGeneral.adapter.AdapterVistaGeneral
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VistaGeneralFragment : Fragment() {

    private lateinit var binding: FragmentVistaGeneralBinding
    private val vistaGeneralViewModel: VistaGeneralViewModel by viewModels()
    private lateinit var adapterVistaGeneral: AdapterVistaGeneral

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVistaGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        binding.rvComidas.layoutManager = LinearLayoutManager(requireContext())
        setAdapter()

        // Observar cambios en las comidas
        setObserver()

        // Obtener todas las comidas al iniciar
        vistaGeneralViewModel.getAllComidas()

        // Configurar SearchView
        binding.svBuscador.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { term ->
                    vistaGeneralViewModel.searchComidasByTerm(term)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { term ->
                    if (term.isEmpty()) {
                        // Si el término está vacío, mostrar todas las comidas
                        vistaGeneralViewModel.getAllComidas()
                    } else {
                        // Buscar comidas por término
                        vistaGeneralViewModel.searchComidasByTerm(term)
                    }
                }
                return true
            }
        })
    }

    private fun setAdapter() {
        adapterVistaGeneral = AdapterVistaGeneral(
            requireContext(),
            listaComidas = emptyList()
        )
        binding.rvComidas.adapter = adapterVistaGeneral
    }

    private fun setObserver() {
        // Observar todas las comidas
        vistaGeneralViewModel.allComidasLiveData.observe(viewLifecycleOwner, { comidas ->
            adapterVistaGeneral.listaComidas = comidas ?: emptyList()
            adapterVistaGeneral.notifyDataSetChanged()
        })

        // Observar comidas filtradas
        vistaGeneralViewModel.filteredComidasLiveData.observe(viewLifecycleOwner, { comidas ->
            adapterVistaGeneral.listaComidas = comidas ?: emptyList()
            adapterVistaGeneral.notifyDataSetChanged()
        })
    }
}