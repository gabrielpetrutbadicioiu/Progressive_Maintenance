package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector


class ValidateCreatedPass {

    fun execute(
         inputPass:String,
         minPassLength:Int,
         maxPassLength:Int,
        ):CreatedPassValidationResult
    {
        //pass length check
        val passLengthColor:Color
        val passLengthIcon:ImageVector
        val passLengthIconColor:Color
        val passLengthValidation:Boolean

        if (inputPass.length in minPassLength..maxPassLength)
        {
            passLengthColor=Color.Green
            passLengthIcon= Icons.Default.CheckCircleOutline
            passLengthIconColor=Color.Green
            passLengthValidation=true
        }
        else{
            passLengthColor=Color.LightGray
            passLengthIcon= Icons.Default.Cancel
            passLengthIconColor=Color.Red
            passLengthValidation=false
        }

        //uppercase letter and number presence check
        val numberRegex = Regex("[0-9]")
        val uppercaseRegex = Regex("[A-Z]")
        val hasNumberAndUppercase= numberRegex.containsMatchIn(inputPass) && uppercaseRegex.containsMatchIn(inputPass)
        val hasNumberAndUpperColor:Color
        val hasNumberAndUpperCaseIcon:ImageVector
        val hasNumberAndUpperIconColor:Color


        if (hasNumberAndUppercase)
        {
            hasNumberAndUpperColor=Color.Green
            hasNumberAndUpperCaseIcon=Icons.Default.CheckCircleOutline
            hasNumberAndUpperIconColor=Color.Green

        }
        else{
            hasNumberAndUpperColor=Color.LightGray
            hasNumberAndUpperCaseIcon=Icons.Default.Cancel
            hasNumberAndUpperIconColor=Color.Red
        }
        //pass strength check
        val isPassStrong= hasNumberAndUppercase && passLengthValidation
        val strongPassColor:Color
        val strongPassIcon:ImageVector
        val strongPassIconColor:Color

        val isPassValid:Boolean
        if(isPassStrong)
        {
            strongPassColor= Color.Green
            strongPassIcon=Icons.Default.CheckCircleOutline
            strongPassIconColor=Color.Green
            isPassValid=true
        }
        else
        {
            strongPassColor= Color.LightGray
            strongPassIcon=Icons.Default.Cancel
            strongPassIconColor=Color.Red
            isPassValid=false
        }
        return CreatedPassValidationResult(
            passLengthColor=passLengthColor,
            passLengthIcon=passLengthIcon,
            passLengthIconColor =passLengthIconColor,

            hasNumberAndUpperColor = hasNumberAndUpperColor,
            hasNumberAndUpperCaseIcon = hasNumberAndUpperCaseIcon,
            hasNumberAndUpperIconColor = hasNumberAndUpperIconColor,

            strongPassColor = strongPassColor,
            strongPassIcon = strongPassIcon,
            strongPassIconColor=strongPassIconColor,

            isPassValid = isPassValid
            )

    }
}