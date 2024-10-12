package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email

import androidx.compose.ui.graphics.vector.ImageVector

data class ValidationResult(
    val isError:Boolean=false,
    val labelMessage:String="Email",
    val labelIcon:ImageVector?=null
)
