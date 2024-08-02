package ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    //one time events
    private val _eventFlow= MutableSharedFlow<UiEvent>()
    val eventFlow=_eventFlow.asSharedFlow()

    sealed class UiEvent{
data class showToast(val message:String):UiEvent()
object SignUp:UiEvent()
    }

    //states
    var showPasswordChecked by mutableStateOf(false)
        private set


    fun onEvent(event: LogInScreenEvent)
    {
        when(event)
        {
            is LogInScreenEvent.onCreateAccClick->
            {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.SignUp)
                }
            }
        }//when
    }//onEvent

}