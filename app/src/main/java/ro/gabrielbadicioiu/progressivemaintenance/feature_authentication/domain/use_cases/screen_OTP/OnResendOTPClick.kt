package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP

import kotlinx.coroutines.delay

class OnResendOTPClick {
    suspend fun execute(
        onIndicatorChange:(Boolean)->Unit,
        onTimerReset:(Int)->Unit,
        initialTimerValue:Int

    )
    {
        onIndicatorChange(true)
        delay(500L)
        onIndicatorChange(false)
        onTimerReset(initialTimerValue)
    }
}