package ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.SignUpScreenEvent
import ro.gabrielbadicioiu.progressivemaintenance.util.Screens


class SignUpScreenViewModel(

):ViewModel() {
//one time events
private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class UiEvent{
    object OnSignUpClickNav:UiEvent()
        data class ShowToast(val message:String):UiEvent()
        object onBackButtonPressed:UiEvent()
    }

    //states
 var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set
    var confirmedPassword by mutableStateOf("")
        private set
    var showPasswordChecked by mutableStateOf(false)
        private set
    var rememberMeChecked by mutableStateOf(false)
        private set
    fun onEvent(event:SignUpScreenEvent)
    {
        when(event)
        {
            is SignUpScreenEvent.EnteredEmail->
            {
                email=event.value
            }
            is SignUpScreenEvent.EnteredPassword->
            {
                password=event.value
            }
            is  SignUpScreenEvent.ConfirmedPassword->
            {
                confirmedPassword=event.value
            }
            is SignUpScreenEvent.OnSignUpBtnClick->
            {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ShowToast("Coming soon!"))
                }

            }
            is SignUpScreenEvent.OnShowPasswordChecked->
            {
                showPasswordChecked=!showPasswordChecked
            }
            is SignUpScreenEvent.OnBackButtonClick->{
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.onBackButtonPressed)
                }
            }

        }
    }

}