package com.example.gymactive.ui.viewmodel.vistageneral

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymactive.domain.Comidas.models.ComidaModel
import com.example.gymactive.domain.VistaGeneral.usercase.GetAllComidasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VistaGeneralViewModel @Inject constructor(
    private val getAllComidasUseCase: GetAllComidasUseCase
):ViewModel(){
    val allComidasLiveData = MutableLiveData<List<ComidaModel>?>()

    fun getAllComidas(){
        viewModelScope.launch(Dispatchers.IO){
            allComidasLiveData.postValue(getAllComidasUseCase())
        }
    }
}
