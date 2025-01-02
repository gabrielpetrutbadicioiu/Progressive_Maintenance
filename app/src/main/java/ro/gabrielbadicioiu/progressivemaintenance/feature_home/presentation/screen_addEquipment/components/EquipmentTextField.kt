package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addEquipment.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R


@Composable
fun EquipmentTextField(
    label:String,
    value:String,
    onValueChange:(String)->Unit,
    isError:Boolean,
    hasTrailingIcon:Boolean
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
            trailingIcon = {if (hasTrailingIcon){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource( id = R.string.icon_descr ))
                }
                } }
        )
    }

}