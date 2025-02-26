package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn


sealed class LoginScreenEvent {

    data class OnEmailChange(val email:String):LoginScreenEvent()
    data class OnPassChange(val pass:String):LoginScreenEvent()

    data object OnShowPassClick:LoginScreenEvent()
    data object OnCheckedChange:LoginScreenEvent()
    data object GetRememberedUser:LoginScreenEvent()
    data object OnSendVerificationEmail:LoginScreenEvent()
    data object OnSignInClick:LoginScreenEvent()
    data object OnRegisterCompanyClick:LoginScreenEvent()
    data object OnSelectCompanyClick:LoginScreenEvent()
}