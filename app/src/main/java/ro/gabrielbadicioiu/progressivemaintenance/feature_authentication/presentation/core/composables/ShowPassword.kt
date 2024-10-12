package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun ShowPasswordCheckBox(checked:Boolean, onCheckedChange:()->Unit)
{
    Box(modifier = Modifier
        .wrapContentSize()
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked, onCheckedChange = {onCheckedChange()})
            Text(text = stringResource(id = R.string.show_password))

        }

        
    }
}