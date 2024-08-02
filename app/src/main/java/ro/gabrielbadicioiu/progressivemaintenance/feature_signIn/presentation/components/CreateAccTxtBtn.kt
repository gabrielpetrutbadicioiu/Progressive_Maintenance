package ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun CreateAccTxtBtn(
    onTxtClick:()->Unit
)
{
    Box {
        TextButton(onClick = { onTxtClick() }) {
            Text(
                text = stringResource(id = R.string.txt_btn),
                fontSize = 16.sp)
            
        }
    }
}