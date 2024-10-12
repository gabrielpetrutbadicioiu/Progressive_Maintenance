package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.ui.graphics.Color

class DoPasswordsMatch {
    fun execute(
        password1:String,
        password2:String,
        isPass1Valid:Boolean
    ):PassMatchingResult
    {
        return if (password1==password2) {
            if(isPass1Valid)
            { PassMatchingResult(
                doPasswordsMatch = true,
                passMatchColor = Color.Green,
                passMatchIcon = Icons.Default.CheckCircleOutline,
                passMatchIconColor = Color.Green,
                arePasswordsValid = true)}
            else{
                PassMatchingResult(
                    doPasswordsMatch = true,
                    passMatchColor = Color.Green,
                    passMatchIcon = Icons.Default.CheckCircleOutline,
                    passMatchIconColor = Color.Green,
                    arePasswordsValid = false)
            }
        } else{
            PassMatchingResult(
                doPasswordsMatch = false,
                passMatchColor = Color.LightGray,
                passMatchIcon = Icons.Default.Cancel,
                passMatchIconColor = Color.Red)
        }
    }
}