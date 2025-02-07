package com.example.gymactive.domain.Comidas.usercase

import com.example.gymactive.data.comida.repository.ComidaRepository
import javax.inject.Inject

class DeleteComidasUseCase @Inject constructor(private val comidaRepository: ComidaRepository){
    suspend operator fun invoke(pos: Int): Boolean {
        return comidaRepository.deleteComida(pos)
    }
}