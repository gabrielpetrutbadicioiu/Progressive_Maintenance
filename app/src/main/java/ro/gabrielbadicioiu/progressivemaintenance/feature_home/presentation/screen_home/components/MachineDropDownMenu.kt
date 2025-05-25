package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider

@Composable
fun MachineDropDownMenu(
    isDropDownMenuExpanded:Boolean,
    onDismissRequest:()->Unit,
    onLogInterventionClick:()->Unit,
    onViewInterventionClick:()->Unit,
    onCreateClClick:()->Unit,
    onViewClClick:()->Unit
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
            text = {
                Column {
                    Text(text = stringResource(id = R.string.view_interventions))
                    Divider(space =16.dp , thickness =1.dp , color = Color.LightGray )
                }
                 },
            onClick = { onViewInterventionClick() })
        DropdownMenuItem(
            text = { Text(text =stringResource(id = R.string.create_cl) )},
            onClick = { onCreateClClick() })
        DropdownMenuItem(
            text = {
                Column {
                    Text(text = stringResource(id = R.string.view_center_line))
                    Divider(space =16.dp , thickness =1.dp , color = Color.LightGray )
                }
            },
            onClick = { onViewClClick() })



    }
}