package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP

sealed class OTPEvent {
    object OnResendBtnClick:OTPEvent()
    object ResendReset:OTPEvent()
}