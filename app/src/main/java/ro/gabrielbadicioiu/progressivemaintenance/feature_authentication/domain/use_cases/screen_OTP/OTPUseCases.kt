package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP

data class OTPUseCases (
    val countDown: CountDown,
    val onResendOTPClick: OnResendOTPClick,
    val otpValidation:OTPValidation
)