package com.example.gymactive.domain.Comidas.usercase

import com.example.gymactive.data.comida.repository.ComidaRepository
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject


class UpdateComidasCaseUse @Inject constructor(private val comidaRepository: ComidaRepository) {
    suspend operator fun invoke(pos:Int, comidaUpdate: Comida):Boolean{
        return comidaRepository.updateComida(pos,comidaUpdate)
    }
}