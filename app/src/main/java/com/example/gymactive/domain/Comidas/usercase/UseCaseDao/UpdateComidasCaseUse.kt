package com.example.gymactive.domain.Comidas.usercase.UseCaseDao

import com.example.gymactive.data.comida.repository.ComidaRepositoryDao
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject


class UpdateComidasCaseUse @Inject constructor(private val comidaRepository: ComidaRepositoryDao) {
    suspend operator fun invoke(pos:Int, comidaUpdate: Comida):Boolean{
        return comidaRepository.updateComida(pos,comidaUpdate)
    }
}