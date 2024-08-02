package ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation

sealed class SignUpScreenEvent {
    data class EnteredEmail(val value:String):SignUpScreenEvent()
    data class EnteredPassword(val value: String):SignUpScreenEvent()
    data class ConfirmedPassword(val value: String):SignUpScreenEvent()
    object OnSignUpBtnClick:SignUpScreenEvent()
    object OnShowPasswordChecked:SignUpScreenEvent()
    object OnBackButtonClick:SignUpScreenEvent()
}