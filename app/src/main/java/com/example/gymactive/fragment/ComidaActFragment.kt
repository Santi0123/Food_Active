package com.example.gymactive.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.gymactive.controller.Controller
import com.example.gymactive.databinding.FragmentComidaBinding

class ComidaActFragment : Fragment() {

    private var _binding: FragmentComidaBinding? = null
    private val binding get() = _binding!!
    lateinit var controller: Controller

    private val openGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            data?.data?.let { uri ->
                Log.d("ComidaActFragment", "Image selected: $uri")
                controller.imageUri = uri
                controller.dialogAgregarComida?.setImageUri(uri) // Actualiza la URI de la imagen en el diálogo
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComidaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("ComidaActFragment", "onViewCreated called")
        controller = Controller(requireContext(), openGalleryLauncher)
        controller.setAdapter(binding)
        controller.initBotonAgregar(binding, this)
        Log.d("ComidaActFragment", "Controller initialized and boton agregar set")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
