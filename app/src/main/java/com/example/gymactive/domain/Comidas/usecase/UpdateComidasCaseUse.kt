package com.example.gymactive.domain.Comidas.usecase

import com.example.gymactive.data.comida.repository.ComidaRepositorio
import com.example.gymactive.domain.Comidas.models.ComidaModel
import javax.inject.Inject


class UpdateComidasCaseUse @Inject constructor(private val repository: ComidaRepositorio) {
    suspend operator fun invoke(comida: ComidaModel):ComidaModel?{
        return repository.updateComida(comida.id!!, comida).getOrElse { null }
    }
}