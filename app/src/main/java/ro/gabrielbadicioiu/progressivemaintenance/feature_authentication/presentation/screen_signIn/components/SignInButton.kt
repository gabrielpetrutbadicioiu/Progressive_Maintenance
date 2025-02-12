package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun SignInButton(
    onButtonClick:()->Unit,
    enable:Boolean
)
{
    Box {
        Button(
            onClick = { onButtonClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp),
            enabled = enable,
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
        ) {
            Text(
                fontSize = 16.sp,
                text = stringResource(id = R.string.signIn_btn_txt),
                modifier = Modifier.padding(0.dp, 6.dp)
            )
        }
    }
}