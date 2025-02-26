package com.example.gymactive.domain.usuario.usecase.network

import com.example.gymactive.data.usuario.repository.UsuarioRepository
import com.example.gymactive.domain.usuario.models.UsuarioModel
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val usuarioRepository: UsuarioRepository) {
    suspend operator fun invoke(email:String,password:String): UsuarioModel? {
        return usuarioRepository.registerApi(email, password)
    }
}
