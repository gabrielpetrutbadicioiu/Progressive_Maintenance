package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP

class OTPValidation {

    fun execute(otp:String,
                onOtpSuccess:()->Unit,
                onOtpFail:()->Unit)
    {
        if(otp=="123456")
        {
            onOtpSuccess()
        }
        else{
            onOtpFail()
        }
    }
}