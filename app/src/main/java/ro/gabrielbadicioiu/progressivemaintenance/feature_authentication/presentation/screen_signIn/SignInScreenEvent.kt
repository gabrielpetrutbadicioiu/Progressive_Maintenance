package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

sealed class SignInScreenEvent {
    object OnCreateAccClick: SignInScreenEvent()
    object OnShowPasswordClick: SignInScreenEvent()
    object OnRememberMeCheck:SignInScreenEvent()
    data class EnteredEmail(val value:String):SignInScreenEvent()
    data class EnteredPassword(val value: String):SignInScreenEvent()

}