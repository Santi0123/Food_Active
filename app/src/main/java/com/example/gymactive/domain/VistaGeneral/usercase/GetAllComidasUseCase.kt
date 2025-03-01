package com.example.gymactive.domain.VistaGeneral.usercase

import com.example.gymactive.data.comida.repository.ComidaRepositorio
import com.example.gymactive.domain.Comidas.models.ComidaModel
import javax.inject.Inject

class GetAllComidasUseCase @Inject constructor(private val comidaRepository: ComidaRepositorio) {

    suspend operator fun invoke(): List<ComidaModel>{
        return comidaRepository.getAllComidas().getOrElse { emptyList() }
    }
}
