package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components


import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun MachineDropDownMenu(
    isDropDownMenuExpanded:Boolean,
    onDismissRequest:()->Unit,
    onLogInterventionClick:()->Unit,
    onViewInterventionClick:()->Unit
)
{
    DropdownMenu(
        expanded = isDropDownMenuExpanded,
        onDismissRequest = { onDismissRequest() }) {

        //logIntervention
        DropdownMenuItem(
            text = {Text(text = stringResource(id = R.string.log_intervention))},
            onClick = { onLogInterventionClick() })
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.view_interventions)) },
            onClick = { onViewInterventionClick() })

    }
}