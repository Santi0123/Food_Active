package com.example.gymactive.ui.viewmodel.Comidas


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymactive.domain.Comidas.models.ComidaModel
import com.example.gymactive.domain.Comidas.usecase.DeleteComidasUseCase
import com.example.gymactive.domain.Comidas.usecase.GetComidaByUserUseCase
import com.example.gymactive.domain.Comidas.usecase.NewComidaUseCase
import com.example.gymactive.domain.Comidas.usecase.UpdateComidasCaseUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComidasViewModel @Inject constructor(
    private val newComidaUseCase: NewComidaUseCase,
    private val getComidaByUser: GetComidaByUserUseCase,
    private val deleteComidaUseCase: DeleteComidasUseCase,
    private val updateComidaCaseUse: UpdateComidasCaseUse,
): ViewModel(){
    val newComidaLiveData = MutableLiveData<ComidaModel?>()
    val posDeleteComidaLiveData = MutableLiveData<ComidaModel?>()
    val posUpdateComidaLiveData = MutableLiveData<ComidaModel?>()
    val comidasLiveData = MutableLiveData<List<ComidaModel>?>()



    fun showComidas(userId:Int) {
         viewModelScope.launch(Dispatchers.IO) {
             val result = getComidaByUser(userId)
             comidasLiveData.postValue(result)
         }
    }

    fun addComida(comida: ComidaModel){
        viewModelScope.launch(Dispatchers.IO){
            val new = newComidaUseCase(comida)
            newComidaLiveData.postValue(new)
        }
    }

    fun deleteComida(comida: ComidaModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = deleteComidaUseCase(comida)
            if (result){
                posDeleteComidaLiveData.postValue(comida)
            }
        }
    }


    fun updateComida(comida: ComidaModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = updateComidaCaseUse(comida)
            posUpdateComidaLiveData.postValue(result)
        }
    }

}