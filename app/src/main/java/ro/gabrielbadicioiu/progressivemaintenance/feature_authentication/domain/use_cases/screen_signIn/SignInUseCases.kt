package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.ShowPassword

data class SignInUseCases (
   val showPassword:ShowPassword,
   val signIn: SignIn,
   val signUp: SignUp
)