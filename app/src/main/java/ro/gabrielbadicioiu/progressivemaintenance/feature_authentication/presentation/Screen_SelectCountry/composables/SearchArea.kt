package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun SearchArea(
     query:String,
     onQueryChange:(String)->Unit,
     onCancelClick:()->Unit
){
    Box{
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top){
            Text(
                text = stringResource(id = R.string.country_hint),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                TextField(
                    value = query,
                    singleLine = true,
                    onValueChange = {onQueryChange(it)},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(32.dp),
                    placeholder = { Text(text =stringResource(id = R.string.Search) )},
                    leadingIcon = { Icon(
                        imageVector = Icons.Default.Search ,
                        contentDescription = stringResource(id =R.string.icon_descr))
                    },
                    colors = TextFieldDefaults.colors(focusedContainerColor = colorResource(id = R.color.light_green),
                        unfocusedContainerColor =  colorResource(id = R.color.light_green),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent)
                )


                TextButton(onClick = { onCancelClick() },
                    modifier = Modifier.padding(8.dp)) {
                    Text(text = stringResource(id = R.string.cancel_btn),
                        color = colorResource(id = R.color.btn_color))
                }

            }

        }
    }
}