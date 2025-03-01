package com.example.gymactive.domain.Comidas.usecase

import com.example.gymactive.data.comida.repository.ComidaRepositoryDao
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject

class GetComidaNaviteUseCase @Inject constructor(private val comidaRepository: ComidaRepositoryDao){
    suspend operator fun invoke():List<Comida>{
        return comidaRepository.getNativeComida()
    }
}