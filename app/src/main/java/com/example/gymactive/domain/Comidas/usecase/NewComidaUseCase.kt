package com.example.gymactive.domain.Comidas.usecase

import com.example.gymactive.data.comida.repository.ComidaRepositorio
import com.example.gymactive.domain.Comidas.models.ComidaModel
import javax.inject.Inject

class NewComidaUseCase @Inject constructor(private val repositorio: ComidaRepositorio) {
    suspend operator fun invoke(newComida: ComidaModel):ComidaModel?{
            return repositorio.addComida(newComida).getOrElse{null}
    }
}