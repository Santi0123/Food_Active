package com.example.gymactive.domain.Comidas.usercase.UseCaseDao

import com.example.gymactive.data.comida.repository.ComidaRepositoryDao
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject

class NewComidaUseCase @Inject constructor(private val comidaRepository: ComidaRepositoryDao) {
    suspend operator fun invoke(newComida: Comida):Comida?{
        return if(!comidaRepository.exisComida(newComida)){
            return comidaRepository.addComida(newComida)
        }else
            null
    }
}