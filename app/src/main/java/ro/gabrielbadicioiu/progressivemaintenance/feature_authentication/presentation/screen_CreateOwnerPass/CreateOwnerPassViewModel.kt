package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

class CreateOwnerPassViewModel:ViewModel() {
    //states
    private val _password = mutableStateOf("")
    val password:State<String> = _password

    private val _confPass = mutableStateOf("")
    val confPass:State<String> = _confPass

    private val _showPass= mutableStateOf(false)
    val showPass:State<Boolean> = _showPass

    private val _showConfPass= mutableStateOf(false)
    val showConfPass:State<Boolean> = _showConfPass

    //one time events

    private val _eventFlow= MutableSharedFlow<CreateOwnerPassUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class CreateOwnerPassUiEvent{
        data object OnNavigateUp:CreateOwnerPassUiEvent()
        data class OnContinueBtnClick(val email:String, val pass:String, val company:Company):CreateOwnerPassUiEvent()
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
                viewModelScope.launch { _eventFlow.emit(
                    CreateOwnerPassUiEvent.OnContinueBtnClick(
                    email = event.email,
                    pass = event.pass,
                    company = event.company
                )) }
                }

            }
        }
    }
