package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email

import android.util.Patterns
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber

class ValidateEmail {

    fun execute(email:String): ValidationResult
    {
        if (email.isBlank())
        {
            return ValidationResult(
                isError = true,
                labelMessage = "\u0020 Email address cannot be empty",
                labelIcon = Icons.Default.WarningAmber

            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return  ValidationResult(
                isError = true,
                labelMessage = "\u0020 Invalid email address. Please try again.",
                labelIcon = Icons.Default.WarningAmber
            )
        }
        return ValidationResult(
            isError = false,
            labelMessage = "Email",
            labelIcon = null


        )
    }
}