package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_createOwnerPass.OwnerPassUseCases

class CreateOwnerPassViewModel(
    private val useCases: OwnerPassUseCases
):ViewModel() {
    //states
    private val _password = mutableStateOf("")
    val password:State<String> = _password

    private val _confPass = mutableStateOf("")
    val confPass:State<String> = _confPass

    private val _showPass= mutableStateOf(false)
    val showPass:State<Boolean> = _showPass

    private val _showConfPass= mutableStateOf(false)
    val showConfPass:State<Boolean> = _showConfPass

    private val _isError= mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _errMsg= mutableStateOf("")
    val errMsg:State<String> = _errMsg
    //one time events

    private val _eventFlow= MutableSharedFlow<CreateOwnerPassUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class CreateOwnerPassUiEvent{
        data object OnNavigateUp:CreateOwnerPassUiEvent()
        data class OnContinueBtnClick(val currentFirebaseUser: FirebaseUser?):CreateOwnerPassUiEvent()
    }
    fun onEvent(event:CreateOwnerPassEvent)
    {
        when(event)
        {
            is CreateOwnerPassEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(CreateOwnerPassUiEvent.OnNavigateUp) }
            }
            is CreateOwnerPassEvent.OnPassChange->{
                _password.value=event.pass
            }
            is CreateOwnerPassEvent.OnConfPassChange->{
                _confPass.value=event.confPass
            }
            is CreateOwnerPassEvent.OnShowPassClick->{
                _showPass.value=!_showPass.value
            }
            is CreateOwnerPassEvent.OnShowConfPassClick->{
                _showConfPass.value=!_showConfPass.value
            }
            is CreateOwnerPassEvent.OnContinueBtnClick->{

                viewModelScope.launch {
                    if (event.poppedBackStack)
                    {
                        viewModelScope.launch {  _eventFlow.emit(CreateOwnerPassUiEvent.OnContinueBtnClick(currentFirebaseUser = Firebase.auth.currentUser)) }
                    }
                    else{
                        useCases.onRegisterUser.execute(
                            email = event.email,
                            pass = _password.value,
                            onSuccess = {currentUser->
                                _isError.value=false
                                _errMsg.value=""
                                viewModelScope.launch {  _eventFlow.emit(CreateOwnerPassUiEvent.OnContinueBtnClick(currentFirebaseUser = currentUser)) }
                            },
                            onFailure = {e->
                                _isError.value=true
                                _errMsg.value=e
                            }
                        )
                    }

                     }
                }

            }
        }
    }
