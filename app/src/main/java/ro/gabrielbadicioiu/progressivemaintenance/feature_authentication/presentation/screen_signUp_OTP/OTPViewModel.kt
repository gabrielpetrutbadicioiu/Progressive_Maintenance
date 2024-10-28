package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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
    var otpError by mutableStateOf(false)
        private set
    var otp by mutableStateOf("")
        private set
    //one time events
    sealed class OTPUiEvent{
        data object OnBackBtnClick: OTPUiEvent()
        data object OnOTPComplete:OTPUiEvent()
    }
    private val _eventFlow= MutableSharedFlow<OTPUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    fun onEvent(event: OTPEvent)
    {
       when(event)
       {
           is OTPEvent.EnteredOTP->{
               otp=event.otp
           }
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


               })

           }}
           is OTPEvent.onBackBtnClick->{
               viewModelScope.launch {
                   _eventFlow.emit(OTPUiEvent.OnBackBtnClick)
               }
           }
           is OTPEvent.OnOTPComplete->{
                   useCases.otpValidation.execute(
                       otp = otp,
                       onOtpSuccess = {
                           viewModelScope.launch {

                               _eventFlow.emit(OTPUiEvent.OnOTPComplete)
                           }
                       },
                       onOtpFail = {
                           otpError=true
                       })

               }

           }
       }
    }



