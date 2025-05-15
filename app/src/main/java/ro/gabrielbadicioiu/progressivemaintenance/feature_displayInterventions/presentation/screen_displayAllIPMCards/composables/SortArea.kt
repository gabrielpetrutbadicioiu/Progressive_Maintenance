package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.domain.model.SortOption

@Composable
fun SortArea(
   sortOption: SortOption,
    onSortByDate:()->Unit,
    onSortByDuration:()->Unit,
   onSortUnresolvedFirst:()->Unit,
   onSortResolvedFirst:()->Unit
)
{
    Column(horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
                RadioButton(
                    selected = sortOption.sortByDate,
                    onClick = { onSortByDate() },
                    colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.btn_color)))
                Text(text = stringResource(id = R.string.sort_by_date) )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
                RadioButton(
                    selected = sortOption.sortByDuration,
                    onClick = { onSortByDuration() },
                    colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.btn_color)))
                Text(text = stringResource(id = R.string.sort_by_duration) )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
                RadioButton(
                    selected = sortOption.unresolvedOnly,
                    onClick = { onSortUnresolvedFirst() },
                    colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.btn_color)))
                Text(text = stringResource(id = R.string.only_unresolved) )
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)){
                RadioButton(
                    selected = sortOption.resolvedOnly,
                    onClick = { onSortResolvedFirst() },
                    colors = RadioButtonDefaults.colors(selectedColor = colorResource(id = R.color.btn_color)))
                Text(text = stringResource(id = R.string.only_resolved) )
            }
        }
    }
}