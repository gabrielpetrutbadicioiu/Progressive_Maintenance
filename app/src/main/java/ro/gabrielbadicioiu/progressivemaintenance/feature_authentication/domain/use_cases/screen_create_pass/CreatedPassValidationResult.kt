package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class CreatedPassValidationResult(
    val passLengthColor: Color=Color.LightGray,
    val passLengthIcon:ImageVector=Icons.Default.Cancel,
    val passLengthIconColor:Color=Color.Red,

    val hasNumberAndUpperColor: Color=Color.LightGray,
    val hasNumberAndUpperCaseIcon:ImageVector=Icons.Default.Cancel,
    val hasNumberAndUpperIconColor:Color=Color.Red,

    val strongPassColor:Color= Color.LightGray,
    val strongPassIcon:ImageVector=Icons.Default.Cancel,
    val strongPassIconColor:Color=Color.Red,

    val isPassValid:Boolean=false
)
