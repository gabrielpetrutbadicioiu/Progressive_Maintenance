package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun NameTextField(
    value:String,
    onValueChange:(String)->Unit,
    label:String,
    icon:ImageVector,
    isError:Boolean
)
{


    Row(modifier = Modifier
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        ) {
        OutlinedTextField(
            modifier = Modifier.wrapContentSize(),
            value =value ,
            isError = isError,
            onValueChange ={
            value->
                if(value.length<=20)
                {
                    onValueChange(value)}
                }, //onValueChange
            label ={
                Row(
                    modifier = Modifier.wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center)
                {
                    if (isError)
                    {
                        Icon(imageVector = Icons.Default.WarningAmber,
                            contentDescription = stringResource(
                            id = R.string.warning_icon_descr
                        ) )
                    }
                    Text(text = label)
                }

                   },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = stringResource(id = R.string.username_icon))}


        )
    }
}