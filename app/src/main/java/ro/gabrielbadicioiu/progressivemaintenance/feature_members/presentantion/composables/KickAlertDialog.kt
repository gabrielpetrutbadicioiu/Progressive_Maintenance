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
fun BanUserAlertDialog(
    tappedUser:MemberStatus,
    onDismissRequest:()->Unit,
    onBanConfirm:()->Unit,
    onUnbanConfirm:()->Unit,
    show:Boolean,
    isBanned:Boolean
)
{
    if (show)
    {
        Box {
            AlertDialog(
                title = {
                    if (isBanned)
                    {
                        Text(
                            text = "${stringResource(id = R.string.ban_alert)} ${tappedUser.user.firstName} ${tappedUser.user.lastName}?")
                    }else{
                        Text(text = "${stringResource(id = R.string.unban)} ${tappedUser.user.firstName} ${tappedUser.user.lastName}?")
                    }


                },
                onDismissRequest = { onDismissRequest() },
                confirmButton = {   Button(onClick = {
                    if (isBanned)
                    {
                        onBanConfirm()
                    }
                    else {onUnbanConfirm()}

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