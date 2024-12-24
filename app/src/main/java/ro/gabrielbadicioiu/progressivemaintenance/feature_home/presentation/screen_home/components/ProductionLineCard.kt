package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import java.util.Locale


@Composable
fun ProductionLineCard(
    lineName:String,
    onExpandClick:()->Unit,
    isExpanded:Boolean,
    lineMachines:List<String>
)
{
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.bar_color)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = lineName.uppercase(Locale.ROOT),
                        style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = { onExpandClick() }) {
                        val icon= if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown
                        Icon(imageVector =icon,
                            contentDescription = stringResource(id = R.string.icon_btn_descr))
                    }

                }
            if(isExpanded){
                Column(
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    lineMachines.forEach {
                        machine->
                        Text(
                            text = machine,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable {
                                    //TODO
                                },
                            style = MaterialTheme.typography.bodyMedium
                        )
                        HorizontalDivider( color = Color.DarkGray)
                    }
                }
            }
        }

    }
}