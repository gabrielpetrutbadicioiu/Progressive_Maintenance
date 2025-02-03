package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun EmptyScreenCard()
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.bar_color)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){
            Text(
                text = stringResource(id = R.string.empty_screen_message),
                color = colorResource(id = R.color.text_color))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.empty_screen_message2),
                    color = colorResource(id = R.color.text_color))
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.icon_descr),
                    tint = colorResource(id = R.color.text_color))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.empty_screen_message3),
                    color = colorResource(id = R.color.text_color))
            }

        }
}
}