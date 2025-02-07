package com.example.gymactive.ui.views.fragment.Comida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.databinding.FragmentComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ListComida
import com.example.gymactive.ui.viewmodel.Comidas.ComidasViewModel
import com.example.gymactive.ui.views.fragment.Comida.adapter.AdapterComida
import com.example.gymactive.ui.views.fragment.Comida.dialog.DialogAgregarComida
import com.example.gymactive.ui.views.fragment.Comida.dialog.DialogBorrarComida
import com.example.gymactive.ui.views.fragment.comida.dialog.DialogEditarComida
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComidaFragment : Fragment() {

    private lateinit var binding: FragmentComidaBinding
    private val comidaViewModel: ComidasViewModel by viewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterComida: AdapterComida

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvComida.layoutManager = LinearLayoutManager(activity)
        comidaViewModel.showComidas()
        setAdapter(ListComida.comidaObject.comidasMutableList.toMutableList())
        setObserver()
        btnAddOnClickListener()
        setScrollWithOffsetLinearLayout()
    }

    private fun setObserver() {
        comidaViewModel.comidasLiveData.observe(viewLifecycleOwner, { comidas ->
            adapterComida.listaComidas = comidas.toMutableList()
            adapterComida.notifyDataSetChanged()
        })
        comidaViewModel.newComidaLiveData.observe(viewLifecycleOwner, { newComida ->
            newComida?.let {
                adapterComida.listaComidas = ListComida.comidaObject.comidasMutableList.toMutableList()
                adapterComida.notifyItemInserted(adapterComida.listaComidas.size -1)
                layoutManager.scrollToPosition(adapterComida.listaComidas.size -1)
            }
        })
        comidaViewModel.posDeleteComidaLiveData.observe(viewLifecycleOwner, { posDel ->
            adapterComida.listaComidas = ListComida.comidaObject.comidasMutableList
            adapterComida.notifyItemRemoved(posDel)
            layoutManager.scrollToPosition(adapterComida.listaComidas.size -1)
        })
        comidaViewModel.posUpdateComidaLiveData.observe(viewLifecycleOwner, { posUpdate ->
            adapterComida.listaComidas = ListComida.comidaObject.comidasMutableList
            adapterComida.notifyItemChanged(posUpdate)
            layoutManager.scrollToPosition(adapterComida.listaComidas.size -1)
        })
    }

    private fun btnAddOnClickListener() {
        binding.btnAgregar.setOnClickListener {
            val dialog = DialogAgregarComida(){
                comida -> comidaViewModel.addComida(comida)
            }
            dialog.show(requireActivity().supportFragmentManager, "Agrego comida")
        }
    }

    private fun setAdapter(listaComida: MutableList<Comida>) {
        adapterComida = AdapterComida(
            listaComida,
            {pos->updateComida(pos)},
            {pos->delComida(pos)}
        )
        this.binding.rvComida.adapter = adapterComida
    }

    private fun delComida(pos: Int) {
        val comida = ListComida.comidaObject.comidasMutableList[pos]
        val dialog =
            DialogBorrarComida(pos,comida){
                pos-> comidaViewModel.deleteComida(pos)
            }
        dialog.show(requireActivity().supportFragmentManager,"Se ha borrado exitosamente")
    }

    private fun updateComida(pos: Int) {
        val comida = ListComida.comidaObject.comidasMutableList[pos]

        val dialog =
            DialogEditarComida(pos,comida){
                pos, comida ->  comidaViewModel.updateComida(pos,comida)
            }
        dialog.show(requireActivity().supportFragmentManager,"Editado correctamente")
    }

    private fun setScrollWithOffsetLinearLayout() {
        if (binding.rvComida.layoutManager is LinearLayoutManager) {
            layoutManager = binding.rvComida.layoutManager as LinearLayoutManager
        }
    }
}
