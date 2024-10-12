package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email

import android.util.Patterns
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.core.ValidationResult

class ValidateEmail {

    fun execute(email:String): ValidationResult
    {
        if (email.isBlank())
        {
            return ValidationResult(
                successful = false,
                errorMessage = "\u0020 Email address cannot be empty"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return  ValidationResult(
                successful = false,
                errorMessage = "\u0020 Invalid email address. Please try again."
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = "Email"

        )
    }
}