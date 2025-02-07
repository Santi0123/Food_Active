package com.example.gymactive.domain.Comidas.usercase

import com.example.gymactive.data.comida.repository.ComidaRepository
import com.example.gymactive.domain.Comidas.models.Comida
import javax.inject.Inject

class GetComidaByPosUseCase @Inject constructor(private val comidaRepository: ComidaRepository) {
    suspend operator fun invoke(pos:Int):Comida?{
        return comidaRepository.getComidaByPos(pos)
    }
}