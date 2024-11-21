package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

sealed class SignInScreenEvent {
    data object OnCreateAccClick: SignInScreenEvent()
    data object OnShowPasswordClick: SignInScreenEvent()
    data object OnRememberMeCheck:SignInScreenEvent()
    data object OnSignInBtnClick:SignInScreenEvent()
    data class EnteredEmail(val value:String):SignInScreenEvent()
    data class EnteredPassword(val value: String):SignInScreenEvent()

}