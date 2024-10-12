package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.graphics.vector.ImageVector

data class ShowPassResult(
    val showPass:Boolean=false,
    val icon:ImageVector= Icons.Default.VisibilityOff
)
