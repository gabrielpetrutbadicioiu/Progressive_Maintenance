package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP


import kotlinx.coroutines.delay



class CountDown
 {

     suspend fun execute(
         currentTimerValue:Int,
         onTick:(Int)->Unit,
         hasTimeExpired:(Boolean)->Unit
     )
     {
         var newValue=currentTimerValue
         while (newValue>=0)
         {
             delay(1000L)
             onTick(newValue)
             hasTimeExpired(newValue==0)
             newValue--

         }
     }
}