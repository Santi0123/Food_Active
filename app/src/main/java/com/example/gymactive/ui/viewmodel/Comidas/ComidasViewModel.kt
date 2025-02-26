package com.example.gymactive.ui.viewmodel.Comidas

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ListComida
import com.example.gymactive.domain.Comidas.usercase.UseCaseDao.DeleteComidasUseCase
import com.example.gymactive.domain.Comidas.usercase.UseCaseDao.GetComidaByPosUseCase
import com.example.gymactive.domain.Comidas.usercase.UseCaseDao.GetComidaNaviteUseCase
import com.example.gymactive.domain.Comidas.usercase.UseCaseDao.GetComidaMLUseCase
import com.example.gymactive.domain.Comidas.usercase.UseCaseDao.NewComidaUseCase
import com.example.gymactive.domain.Comidas.usercase.UseCaseDao.UpdateComidasCaseUse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComidasViewModel @Inject constructor(
    private val newComidaUseCase: NewComidaUseCase,
    private val getComidaForPosUseCase: GetComidaByPosUseCase,
    private val deleteComidaUseCase: DeleteComidasUseCase,
    private val updateComidaCaseUse: UpdateComidasCaseUse,
    private val getNativeComidasUseCase: GetComidaNaviteUseCase,
    private val getComidaUseCase: GetComidaMLUseCase
): ViewModel(){
    val newComidaLiveData = MutableLiveData<Comida>()
    val posDeleteComidaLiveData = MutableLiveData<Int>()
    val posUpdateComidaLiveData = MutableLiveData<Int>()
    val comidasLiveData = MutableLiveData<List<Comida>>()
    val detailComidaLiveData = MutableLiveData<Comida>()

    fun showComidas() {
        viewModelScope.launch(Dispatchers.IO) {
            if (ListComida.comidaObject.comidasMutableList.isEmpty()) {
                val data = getNativeComidasUseCase()
                ListComida.comidaObject.comidasMutableList.addAll(data)
            }
            comidasLiveData.postValue(ListComida.comidaObject.comidasMutableList)
        }
    }

    suspend fun getComidaPosition(pos: Int){
        val comida = getComidaForPosUseCase(pos)
        comida?.let{
            detailComidaLiveData.value = comida!!
        }
    }

    fun deleteComida(pos:Int){
        viewModelScope.launch(Dispatchers.IO){
            val result  = deleteComidaUseCase(pos)
            if (result)
                posDeleteComidaLiveData.postValue(pos)
        }
    }

    fun updateComida(pos:Int,comida: Comida){
        viewModelScope.launch(Dispatchers.IO){
            val result = updateComidaCaseUse(pos,comida)
            if(result){
                posUpdateComidaLiveData.postValue(pos)
            }
        }
    }

    fun addComida(comida: Comida){
        viewModelScope.launch(Dispatchers.IO){
            val new = newComidaUseCase(comida)
            new?.let {
                newComidaLiveData.postValue(new!!)
            }
        }
    }
}