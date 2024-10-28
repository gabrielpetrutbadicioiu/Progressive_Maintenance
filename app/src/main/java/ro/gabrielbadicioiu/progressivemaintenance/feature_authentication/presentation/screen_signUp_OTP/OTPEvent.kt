package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP

sealed class OTPEvent {
    object startTimer:OTPEvent()
    object onResendOTPClick:OTPEvent()
    object onBackBtnClick:OTPEvent()
    object OnOTPComplete:OTPEvent()
    data class EnteredOTP(val otp:String):OTPEvent()

}