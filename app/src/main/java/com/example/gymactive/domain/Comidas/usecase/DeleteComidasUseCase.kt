package com.example.gymactive.domain.Comidas.usecase

import com.example.gymactive.data.comida.repository.ComidaRepositorio
import com.example.gymactive.domain.Comidas.models.ComidaModel
import javax.inject.Inject

class DeleteComidasUseCase @Inject constructor(private val comidaRepository: ComidaRepositorio){
    suspend operator fun invoke(comidaModel: ComidaModel): Boolean {
        return comidaRepository.deleteComida(comidaModel.id!!).getOrElse { false }
    }
}