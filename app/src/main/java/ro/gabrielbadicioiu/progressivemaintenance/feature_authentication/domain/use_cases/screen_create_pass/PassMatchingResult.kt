package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class PassMatchingResult(
     val doPasswordsMatch:Boolean=false,
     val arePasswordsValid:Boolean=false,

     val passMatchColor:Color= Color.LightGray,
     val passMatchIcon:ImageVector= Icons.Default.Cancel,
     val passMatchIconColor:Color=Color.Red
 )
