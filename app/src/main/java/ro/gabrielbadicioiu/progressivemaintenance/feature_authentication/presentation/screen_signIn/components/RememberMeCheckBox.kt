package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun RememberMe(
    checked:Boolean,
    onCheckedChange:()->Unit
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked,
            onCheckedChange ={onCheckedChange()}, )
        Text(text = stringResource(id = R.string.remember_me))
    }
}