import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymactive.domain.usuario.models.UsuarioModel
import com.example.gymactive.domain.usuario.usecase.network.LoginUseCase
import com.example.gymactive.domain.usuario.usecase.network.RegisterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsuarioViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    val loginLiveData = MutableLiveData<UsuarioModel?>()
    val registerLiveData = MutableLiveData<UsuarioModel?>()

    val usuarioLiveData = MutableLiveData<UsuarioModel?>()


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var usuarioLogeado: UsuarioModel?= null
            if(email.isNotEmpty() && password.isNotEmpty()){
                usuarioLogeado = loginUseCase(email, password)
            }
            withContext(Dispatchers.Main){
                usuarioLiveData.value = usuarioLogeado
            }
        }
    }

    fun register(usuario: UsuarioModel) {
        viewModelScope.launch(Dispatchers.IO) {
            var usuarioRegistrado: UsuarioModel?= null
            usuarioRegistrado = registerUseCase(usuario)
            withContext(Dispatchers.Main) {
                registerLiveData.value = usuarioRegistrado
            }
        }
    }
}
