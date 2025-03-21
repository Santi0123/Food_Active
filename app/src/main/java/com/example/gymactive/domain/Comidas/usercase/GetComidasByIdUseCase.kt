package com.example.gymactive.domain.Comidas.usercase

import com.example.gymactive.data.comida.repository.ComidaRepository
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject

class GetComidasByIdUseCase  @Inject constructor(private val comidaRepository: ComidaRepository) {
    suspend operator fun invoke(id: Int): Comida? {
        return comidaRepository.getComidaById(id)
    }
}
