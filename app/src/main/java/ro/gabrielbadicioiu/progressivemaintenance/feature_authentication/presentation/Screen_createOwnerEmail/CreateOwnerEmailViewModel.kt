package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_CreateOwnerEmail.CreateOwnerAccUseCases

class CreateOwnerEmailViewModel(private val useCases: CreateOwnerAccUseCases):ViewModel() {

    //states
    private val _isExpanded= mutableStateOf(false)
    val isExpanded:State<Boolean> = _isExpanded

    private val _inputEmail= mutableStateOf("")
    val inputEmail:State<String> = _inputEmail

    private val _isErr = mutableStateOf(false)
    val isErr:State<Boolean> = _isErr
    //one time events
    private val _eventFlow= MutableSharedFlow<CreateOwnerAccountUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()
    sealed class CreateOwnerAccountUiEvent{
        data object NavigateToCompanyDetailsScreen:CreateOwnerAccountUiEvent()
        data class NavigateToOwnerPass(val email:String):CreateOwnerAccountUiEvent()
    }
    fun onEvent(event: CreateOwnerEmailEvent)
    {
        when(event)
        {
            is CreateOwnerEmailEvent.OnExpandTextClick->{
                _isExpanded.value=!_isExpanded.value
            }
            is CreateOwnerEmailEvent.OnNavigateToCompanyDetailsScreen->{
                viewModelScope.launch { _eventFlow.emit(CreateOwnerAccountUiEvent.NavigateToCompanyDetailsScreen) }
            }
            is CreateOwnerEmailEvent.OnEmailChange->{
                _inputEmail.value=event.email
            }
            is CreateOwnerEmailEvent.OnContinueBtnClick->{
                useCases.onValidateEmailFormat.execute(
                    email = _inputEmail.value,
                    onSuccess = {
                        _isErr.value=false
                        viewModelScope.launch { _eventFlow.emit(CreateOwnerAccountUiEvent.NavigateToOwnerPass(email = _inputEmail.value)) }
                    },
                    onError = {
                        _isErr.value=true
                    })
            }
        }
    }
}