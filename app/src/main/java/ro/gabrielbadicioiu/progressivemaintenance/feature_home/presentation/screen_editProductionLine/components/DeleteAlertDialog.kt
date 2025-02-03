package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun DeleteAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
)
{
    if(showDialog)
    {
        AlertDialog(
            title = {
                Text(text = stringResource(id = R.string.delete_line_warning))
            },
            onDismissRequest = { onDismiss() },
            confirmButton = { Button(onClick = { onConfirm()}) {
                Text(stringResource(id = R.string.delete_line_confirm))
            } },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(text = stringResource(id = R.string.delete_line_dismiss))
                }
            })
    }
}