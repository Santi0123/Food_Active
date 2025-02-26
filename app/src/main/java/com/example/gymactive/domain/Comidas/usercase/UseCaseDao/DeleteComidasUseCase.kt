package com.example.gymactive.domain.Comidas.usercase.UseCaseDao

import com.example.gymactive.data.comida.repository.ComidaRepositoryDao
import javax.inject.Inject

class DeleteComidasUseCase @Inject constructor(private val comidaRepository: ComidaRepositoryDao){
    suspend operator fun invoke(pos: Int): Boolean {
        return comidaRepository.deleteComida(pos)
    }
}