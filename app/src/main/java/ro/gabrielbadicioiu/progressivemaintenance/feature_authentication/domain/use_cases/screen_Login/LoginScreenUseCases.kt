package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.FetchRegisteredCompanies
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnCompanySearch

data class LoginScreenUseCases(
    val rememberUser: RememberUser,
    val getRememberedUser: GetRememberedUser,
    val onSignIn: OnSignInClick,
    val sendVerificationEmail: SendVerificationEmail,
    val getCurrentFirebaseUser: GetCurrentFirebaseUser,
    val checkUserInCompany: CheckUserInCompany,
    val fetchRegisteredCompanies: FetchRegisteredCompanies,
    val onCompanySearch: OnCompanySearch

)
