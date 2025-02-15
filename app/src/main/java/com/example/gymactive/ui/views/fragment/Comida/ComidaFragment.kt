package com.example.gymactive.ui.views.fragment.Comida

import DialogEditarComida
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymactive.R
import com.example.gymactive.databinding.FragmentComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ListComida
import com.example.gymactive.ui.viewmodel.Comidas.ComidasViewModel
import com.example.gymactive.ui.views.activities.Login
import com.example.gymactive.ui.views.fragment.Comida.adapter.AdapterComida
import com.example.gymactive.ui.views.fragment.Comida.dialog.DialogBorrarComida
import com.example.gymactive.ui.views.fragment.comida.dialog.DialogAgregarComida
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComidaFragment : Fragment() {

    private lateinit var binding: FragmentComidaBinding
    private val comidaViewModel: ComidasViewModel by viewModels()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterComida: AdapterComida
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences



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
        autenticacion()
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

    private fun autenticacion() {
        auth = Firebase.auth
        sharedPreferences = requireContext().getSharedPreferences("session_prefs", MODE_PRIVATE)
        // Verifica si hay una sesión iniciada en SharedPreferences
        val isLogged = sharedPreferences.getBoolean("is_logged_in", false)

        // Verifica si el usuario está autenticado
        val currentUser = auth.currentUser
        if (!isLogged || currentUser == null || !currentUser.isEmailVerified) {
            //Toast.makeText(this, "Redirigiendo a login.", Toast.LENGTH_LONG).show()
            startActivity(Intent(requireContext(), Login::class.java))
            requireActivity().finish()
        }
        val userName = requireActivity().findViewById<TextView>(R.id.userNameTextView)
        val userEmail = requireActivity().findViewById<TextView>(R.id.userEmailTextView)
        if(currentUser != null){
            val emailUser = currentUser.email.toString().split("0")
            userName.setText(emailUser[0])
            userEmail.setText(currentUser.email.toString())
        }
    }
}
