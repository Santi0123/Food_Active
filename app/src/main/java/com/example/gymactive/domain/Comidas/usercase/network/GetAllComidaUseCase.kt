package com.example.gymactive.domain.Comidas.usercase.network

import com.example.gymactive.data.comida.repository.ComidaRepositorio
import com.example.gymactive.domain.Comidas.models.ComidaModel
import javax.inject.Inject


class GetAllComidaUseCase @Inject constructor(private val comidaRepositorio: ComidaRepositorio) {
    /*suspend operator fun invoke(): <List<ComidaModel> {
        return comidaRepositorio.getAllComidas()
    }*/
}