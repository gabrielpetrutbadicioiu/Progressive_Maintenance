package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_registerCompanyEmail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegisterCompanyEmailViewModel(

):ViewModel() {

    //states
    private val _businessEmail= mutableStateOf("")
    val businessEmail:State<String> = _businessEmail
}