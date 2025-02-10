package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login

data class LoginScreenUseCases(
    val rememberUser: RememberUser,
    val getRememberedUser: GetRememberedUser,
    val onSignInClick: OnSignInClick,
    val sendVerificationEmail: SendVerificationEmail,
    val getCurrentFirebaseUser: GetCurrentFirebaseUser,

)
