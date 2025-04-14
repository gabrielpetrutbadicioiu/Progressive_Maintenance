package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.MemberStatus

@Composable
fun KickUserAlertDialog(
    tappedUser:MemberStatus,
    onDismissRequest:()->Unit,
    onConfirm:()->Unit,
    show:Boolean
)
{
    if (show)
    {
        Box {
            AlertDialog(
                title = {
                    Text(
                        text = "${stringResource(id = R.string.kick_alert1)} ${tappedUser.user.firstName} ${tappedUser.user.lastName} ${stringResource(
                            id = R.string.kick_alert2 )} ${stringResource(id = R.string.cannot_undone_action)}")
                },
                onDismissRequest = { onDismissRequest() },
                confirmButton = {   Button(onClick = {
                    onConfirm()
                },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color))) {
                    Text(text = stringResource(id = R.string.done_btn))
                } },
                dismissButton = {
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