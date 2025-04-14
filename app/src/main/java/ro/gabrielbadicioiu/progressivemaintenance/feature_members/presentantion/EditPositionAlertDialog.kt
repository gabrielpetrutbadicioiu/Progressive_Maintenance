package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun EditPositionAlertDialog(
    show:Boolean,
    onDismissRequest:()->Unit,
    onConfirm:()->Unit,
    onValueChange:(String)->Unit,
    value:String
)
{
    if (show)
    {

        Box {
            AlertDialog(
                title = {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        value = value,
                        onValueChange = { position->
                            onValueChange(position)
                                        },
                        placeholder = { Text(text = stringResource(id = R.string.position)) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Work,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        supportingText = { Text(text = stringResource(id = R.string.edit_position_placeholder)) },
                        isError = false
                    )
                },
                onDismissRequest = { onDismissRequest() },
                confirmButton = {    Button(onClick = {onConfirm()},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))) {
                    Text(text = stringResource(id = R.string.done_btn))
                } },
                dismissButton =
                {
                    Button(onClick = {
                    onDismissRequest()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))) {
                    Text(text = stringResource(id = R.string.cancel_btn))
                }
                })
        }
    }

}