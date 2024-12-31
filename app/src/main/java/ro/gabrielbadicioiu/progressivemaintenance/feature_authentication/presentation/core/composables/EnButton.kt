package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun EnButton(
    onButtonClick:()->Unit,
    btnEnabled:Boolean,
    text:String
)
{
    Box {
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color)),
            onClick = { onButtonClick() },
            enabled = btnEnabled,
            modifier = Modifier
                .padding(16.dp, 0.dp)
        ) {
            Text(
                fontSize = 16.sp,
                text = text,
                modifier = Modifier.padding(0.dp, 6.dp)
            )
        }
    }
}