package com.example.gymactive.domain.Comidas.usercase.UseCaseDao

import com.example.gymactive.data.comida.repository.ComidaRepositoryDao
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject

class GetComidasByIdUseCase  @Inject constructor(private val comidaRepository: ComidaRepositoryDao) {
    suspend operator fun invoke(id: Int): Comida? {
        return comidaRepository.getComidaById(id)
    }
}
