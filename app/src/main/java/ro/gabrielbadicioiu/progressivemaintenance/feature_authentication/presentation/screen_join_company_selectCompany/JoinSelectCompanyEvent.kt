package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_join_company_selectCompany

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

sealed class JoinSelectCompanyEvent {

    data class OnCompanyClick(val selectedCompany:Company):JoinSelectCompanyEvent()
    data class OnRegistrationEmailChange(val newValue:String):JoinSelectCompanyEvent()
    data class OnCompanySearch(val searchedValue:String):JoinSelectCompanyEvent()
    data class OnOtpValueChange(val enteredOTP:String):JoinSelectCompanyEvent()

    data object OnShowDialogClick:JoinSelectCompanyEvent()
    data object OnFetchRegisteredCompanies:JoinSelectCompanyEvent()
    data object OnDialogDismiss:JoinSelectCompanyEvent()
}