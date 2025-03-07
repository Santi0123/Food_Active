package com.example.gymactive.ui.viewmodel.vistageneral

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymactive.domain.Comidas.models.ComidaModel
import com.example.gymactive.domain.Comidas.usecase.GetComidaByTermUseCase
import com.example.gymactive.domain.VistaGeneral.usercase.GetAllComidasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VistaGeneralViewModel @Inject constructor(
    private val getAllComidasUseCase: GetAllComidasUseCase,
    private val getComidaByTermUseCase: GetComidaByTermUseCase // Inyectar el nuevo caso de uso
) : ViewModel() {

    val allComidasLiveData = MutableLiveData<List<ComidaModel>?>()

    val filteredComidasLiveData = MutableLiveData<List<ComidaModel>?>()

    fun getAllComidas() {
        viewModelScope.launch(Dispatchers.IO) {
            allComidasLiveData.postValue(getAllComidasUseCase())
        }
    }

    fun searchComidasByTerm(term: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getComidaByTermUseCase(term)
            if (result.isSuccess) {
                filteredComidasLiveData.postValue(result.getOrNull())
            } else {
                // Manejar el error (opcional)
                filteredComidasLiveData.postValue(null)
            }
        }
    }
}