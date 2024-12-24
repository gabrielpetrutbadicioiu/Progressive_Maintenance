package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun IconTextField(
    text:String,
    icon:ImageVector,
    color:Color,
    iconSize:Int,
    textSize:Int,
    clickEn:Boolean,
    onClick:()->Unit
)
{
    Row(
        modifier= Modifier.fillMaxWidth().clickable(
            enabled = clickEn
        )
        {
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        )
    {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(id =R.string.warning_icon_descr),
            tint = color,
            modifier = Modifier.size(iconSize.dp)
            )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = text,
            color = color,
            fontSize = textSize.sp)


    }
}