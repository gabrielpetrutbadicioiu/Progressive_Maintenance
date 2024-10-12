package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OTPViewModel:ViewModel() {
    var countDownTimer by mutableStateOf(3)
        private set
    var resendCode by mutableStateOf(false)
        private set
    var resendProgress by mutableStateOf(false)
        private set


    fun onEvent(event: OTPEvent)
    {
        when(event)
        {
            is OTPEvent.OnResendBtnClick->{
                countDownTimer=3
                resendCode=false
                resendProgress=true
                CountDown()

            }
            is OTPEvent.ResendReset->{
                resendProgress=false


            }
        }
    }

    fun CountDown()
    {
        viewModelScope.launch {
            delay(750L)
            while (countDownTimer>0)
            {
                countDownTimer--
                delay(1000L)
            }
            resendCode= countDownTimer==0

        }
    }

}