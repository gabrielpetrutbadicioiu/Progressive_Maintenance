package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie

@Composable
fun MachineDropDownMenu(
    isDropDownMenuExpanded:Boolean,
    onDismissRequest:()->Unit,
    onLogInterventionClick:()->Unit
)
{
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { onDismissRequest() }) {

        //logIntervention
        DropdownMenuItem(
            text = { 
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(id = R.string.log_intervention))
                    Spacer(modifier = Modifier.width(2.dp))
                    DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.fix_animation), size = 48.dp)
                }
                 },
            onClick = { onLogInterventionClick() })

    }
}