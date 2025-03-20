package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company


sealed class LoginScreenEvent {

    data class OnEmailChange(val email:String):LoginScreenEvent()
    data class OnPassChange(val pass:String):LoginScreenEvent()
    data class OnCompanySearch(val query:String):LoginScreenEvent()
    data class OnCompanyClick(val selectedCompany: Company):LoginScreenEvent()

    data object OnShowPassClick:LoginScreenEvent()
    data object OnCheckedChange:LoginScreenEvent()
    data object GetRememberedUser:LoginScreenEvent()
    data object OnSendVerificationEmail:LoginScreenEvent()
    data object OnSignInClick:LoginScreenEvent()
    data object OnRegisterCompanyClick:LoginScreenEvent()
    data object OnSelectCompanyClick:LoginScreenEvent()
    data object OnFetchRegisteredCompanies:LoginScreenEvent()
    data object OnCancelDialogClick:LoginScreenEvent()
    data object OnRememberUser:LoginScreenEvent()
    data object OnJoinCompanyClick:LoginScreenEvent()
    data object OnCountDown:LoginScreenEvent()



}