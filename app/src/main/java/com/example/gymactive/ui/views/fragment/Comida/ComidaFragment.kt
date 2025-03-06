package com.example.gymactive.ui.views.fragment.Comida

import DialogEditarComida
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.databinding.FragmentComidaBinding
import com.example.gymactive.domain.Comidas.models.ComidaModel
import com.example.gymactive.domain.Comidas.models.ListComida
import com.example.gymactive.ui.viewmodel.Comidas.ComidasViewModel
import com.example.gymactive.ui.views.activities.Login
import com.example.gymactive.ui.views.fragment.Comida.adapter.AdapterComida
import com.example.gymactive.ui.views.fragment.Comida.dialog.DialogBorrarComida
import com.example.gymactive.ui.views.fragment.comida.dialog.DialogAgregarComida
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComidaFragment : Fragment() {

    private lateinit var binding: FragmentComidaBinding
    private val comidaViewModel: ComidasViewModel by viewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterComida: AdapterComida
    private lateinit var sharedPreferences: SharedPreferences
    var userId : Int = -1



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPreferences = requireContext().getSharedPreferences("session_prefs", MODE_PRIVATE)
        binding = FragmentComidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        autenticacion()

        binding.rvComida.layoutManager = LinearLayoutManager(activity)
        setAdapter()
        setObserver()
        btnAddOnClickListener()
        setScrollWithOffsetLinearLayout()

        val userId = sharedPreferences.getInt("userId",-1)
        comidaViewModel.showComidas(userId)
    }

    private fun setAdapter() {
        adapterComida = AdapterComida(
            requireContext(),
            listaComidas = emptyList<ComidaModel>().toMutableList(),
            {comida-> updateComida(comida)},
            {comida-> delComida(comida)}
        )
        this.binding.rvComida.adapter = adapterComida
    }

    private fun setObserver() {
        comidaViewModel.comidasLiveData.observe(viewLifecycleOwner, { comidas ->
            if (comidas != null){
                adapterComida.listaComidas = comidas.toMutableList()
            }
            adapterComida.notifyDataSetChanged()
        })
        comidaViewModel.newComidaLiveData.observe(viewLifecycleOwner, { newComida ->
            newComida?.let {
                adapterComida.listaComidas.add(it)
                adapterComida.notifyItemInserted(adapterComida.listaComidas.lastIndex)
                layoutManager.scrollToPosition(adapterComida.listaComidas.lastIndex)
            }
        })
        comidaViewModel.posDeleteComidaLiveData.observe(viewLifecycleOwner, { comidaDeleted ->
           // Verificar si la posición es válida
            val index = adapterComida.listaComidas.indexOfFirst { it.id == comidaDeleted?.id  }
            if (index != -1) {
                adapterComida.listaComidas.removeAt(index)
                adapterComida.notifyItemRemoved(index)
                layoutManager.scrollToPositionWithOffset(index, adapterComida.listaComidas.size)
            }
        })
        comidaViewModel.posUpdateComidaLiveData.observe(viewLifecycleOwner, { comidaUpdate  ->

            val index = adapterComida.listaComidas.indexOfFirst { it.id == comidaUpdate?.id }
            if (index != -1) {
                adapterComida.listaComidas[index] = comidaUpdate!!
                adapterComida.notifyItemChanged(index)
                layoutManager.scrollToPositionWithOffset(index, adapterComida.listaComidas.size)
            }
        })
    }

    private fun btnAddOnClickListener() {
        binding.btnAgregar.setOnClickListener {
            val dialog = DialogAgregarComida(userId){
                comida -> comidaViewModel.addComida(comida)
            }
            dialog.show(requireActivity().supportFragmentManager, "Agrego comida")
        }
    }

    private fun delComida (comidaModel: ComidaModel) {
        val dialog =
            DialogBorrarComida(comidaModel){
                comida-> comidaViewModel.deleteComida(comida)
            }
        dialog.show(requireActivity().supportFragmentManager,"Se ha borrado exitosamente")
    }

    private fun updateComida(comidaModel: ComidaModel) {

        val dialog =
            DialogEditarComida(comidaModel){
                 comida ->  comidaViewModel.updateComida(comida)
            }
        dialog.show(requireActivity().supportFragmentManager,"Editado correctamente")
    }

    private fun setScrollWithOffsetLinearLayout() {
        if (binding.rvComida.layoutManager is LinearLayoutManager) {
            layoutManager = binding.rvComida.layoutManager as LinearLayoutManager
        }
    }


    private fun autenticacion() {
        // Verifica si hay una sesión iniciada en SharedPreferences
        val isLogged = sharedPreferences.getBoolean("is_logged_in", false)

        if (!isLogged) {
            resetShared()
            requireActivity().runOnUiThread{
                startActivity((Intent(requireContext(),Login::class.java)))
                requireActivity().finishAffinity()
            }
        }else{
            userId = sharedPreferences.getInt("userId",-1)
        }
    }

    private fun resetShared(){
        with(sharedPreferences.edit()){
            putInt("userId", -1)
            putString("email","")
            putString("nombre","")
            putBoolean("is_logged_in",false)
            apply()
        }
    }
}
