package com.example.gymactive.domain.Comidas.usecase

import com.example.gymactive.data.comida.repository.ComidaRepositorio
import com.example.gymactive.domain.Comidas.models.ComidaModel
import javax.inject.Inject

class GetComidaByUserUseCase @Inject constructor(private val repository: ComidaRepositorio) {
    suspend operator fun invoke(userId:Int): List<ComidaModel>{
        return repository.getComidasByUser(userId).getOrElse { emptyList() }
    }
}