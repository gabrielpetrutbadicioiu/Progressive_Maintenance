package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            onClick = { onButtonClick() },
            enabled = btnEnabled,
            modifier = Modifier
                .fillMaxWidth()
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