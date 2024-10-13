package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.viewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP.OTPUseCases

class OTPViewModel(
    private val useCases:OTPUseCases
):ViewModel() {

    var countDownValue by mutableIntStateOf(60)
        private set
    var hasTimeExpired by mutableStateOf(false)
        private set
    var showIndicator by mutableStateOf(false)
        private set

    fun onEvent(event: OTPEvent)
    {
       when(event)
       {
           is OTPEvent.startTimer->
           {
               viewModelScope.launch {

            useCases.countDown.execute(
                currentTimerValue = countDownValue,
                onTick = {newValue->
                    countDownValue=newValue
                },
                hasTimeExpired = {
                    timerStatus->
                    hasTimeExpired=timerStatus
                }
            )
               }
           }
           is OTPEvent.onResendOTPClick->{
               viewModelScope.launch {
                   useCases.onResendOTPClick.execute(
                       onIndicatorChange = {
                           value->
                           showIndicator=value
                       },
                       initialTimerValue = 60,
                       onTimerReset = {
                           value->
                           countDownValue=value
                           hasTimeExpired=false
                           onEvent(OTPEvent.startTimer)
                       }

                   )
               }

           }
       }
    }



}