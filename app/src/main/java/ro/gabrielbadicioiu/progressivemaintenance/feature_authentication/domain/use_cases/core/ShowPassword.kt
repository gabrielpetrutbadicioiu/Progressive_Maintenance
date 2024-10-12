package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

class ShowPassword {
    fun execute(
        showPassResult: ShowPassResult
    ): ShowPassResult
    {
        val showPass=!showPassResult.showPass
        val icon=if (showPass) Icons.Default.Visibility else Icons.Default.VisibilityOff
      return  showPassResult.copy(showPass = showPass, icon = icon)

    }
}