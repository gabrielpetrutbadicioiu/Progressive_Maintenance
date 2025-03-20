package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserPassword

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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_JoinCompanyUserPass.UserPassUseCases

class JoinCompanyUserPasswordScreenViewModel(
private val useCases: UserPassUseCases
):ViewModel() {
    //states
    private val _password= mutableStateOf("")
    val password:State<String> = _password

    private val _showProgressBar= mutableStateOf(false)
    val showProgressBar:State<Boolean> = _showProgressBar

    private val _showPass= mutableStateOf(false)
    val showPass:State<Boolean> = _showPass

    private val _confPass = mutableStateOf("")
    val confPass:State<String> = _confPass

    private val _showConfPass= mutableStateOf(false)
    val showConfPass:State<Boolean> = _showConfPass

    private val _isError = mutableStateOf(false)
    val isError:State<Boolean> = _isError

    private val _errorMessage = mutableStateOf("")
    val errorMessage:State<String> = _errorMessage

    private var _currentUser:FirebaseUser?= null
    //one time events
    private val _eventFlow = MutableSharedFlow<JoinCompanyUserPassUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()
    sealed class JoinCompanyUserPassUiEvent{
        data object OnNavigateUp:JoinCompanyUserPassUiEvent()
        data class OnNavigateToUserProfile(val userID:String):JoinCompanyUserPassUiEvent()
    }

    fun onEvent(event: JoinCompanyUserPassEvent)
    {
        when(event)
        {
            is JoinCompanyUserPassEvent.OnPasswordChange->{ _password.value=event.pass }

            is JoinCompanyUserPassEvent.OnShowHidePassClick->{ _showPass.value=!_showPass.value}

            is JoinCompanyUserPassEvent.OnNavigateUp->{
                viewModelScope.launch { _eventFlow.emit(JoinCompanyUserPassUiEvent.OnNavigateUp) }
            }

            is JoinCompanyUserPassEvent.OnConfPassChange->{_confPass.value=event.confPass}

            is JoinCompanyUserPassEvent.OnShowHideConfPassClick->{_showConfPass.value=!_showConfPass.value}

            is JoinCompanyUserPassEvent.OnContinueBtnClick->{
                viewModelScope.launch {
                    if (event.hasPoppedBackstack)
                    {
                        val id= Firebase.auth.currentUser?.uid
                        viewModelScope.launch { _eventFlow.emit(JoinCompanyUserPassUiEvent.OnNavigateToUserProfile(userID = id.toString())) }
                    }
                    else{
                        _showProgressBar.value=true
                        useCases.onRegisterUser.execute(
                            email = event.email,
                            pass = _password.value,
                            onSuccess = {currentUser->

                                _isError.value=false
                                _currentUser=currentUser
                                if (currentUser != null) {
                                    viewModelScope.launch {
                                        _eventFlow.emit(JoinCompanyUserPassUiEvent.OnNavigateToUserProfile(userID = currentUser.uid))
                                    }
                                    _showProgressBar.value=false
                                }
                            },
                            onFailure = {e->
                                _showProgressBar.value=false
                                _isError.value=true
                                _errorMessage.value=e}
                        )
                    }
                }

            }
        }
    }



}