package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.components


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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun PassStrengthTextField(
    text:String,
    imageVector: ImageVector,
    textColor: Color,
    iconColor: Color
)
{
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically)
        {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier.size(18.dp),
                tint = iconColor,
                imageVector = imageVector,
                contentDescription = stringResource(id = R.string.check_icon))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp,
                text =text,
                color = textColor)
        }//ROW



}