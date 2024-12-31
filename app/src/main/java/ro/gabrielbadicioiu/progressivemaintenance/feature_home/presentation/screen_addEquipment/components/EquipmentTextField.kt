package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun EquipmentTextField(
    label:String,
    value:String,
    onValueChange:(String)->Unit,
    isError:Boolean
)
{
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it)},
            label = { Text(text = label)},
            isError = isError,
        )
    }

}