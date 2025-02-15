package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun PasswordRequirements (
    password:String,
    confPassword:String
)
{
Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxWidth()
    )
{
    val isPassStrong=password.matches(Regex(".*[A-Z].*")) && password.matches(Regex(".*\\d.*"))
    val doPassMatches=password==confPassword && password.isNotEmpty()
    //pass size
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically)
    {
        if (password.length<8)
        {
            Icon(imageVector = Icons.Default.Block,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Red)
        }
        else{
            Icon(imageVector = Icons.Default.CheckCircleOutline,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Green)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(id = R.string.pass_chars),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold)
    }

    //pass strength
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically)
    {
        if (isPassStrong)
        {
            Icon(imageVector = Icons.Default.CheckCircleOutline,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Green)
        }
        else{
            Icon(imageVector = Icons.Default.Block,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Red)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(id = R.string.pass_container),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold)
    }
    //pass strength
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically)
    {
        if (isPassStrong)
        {
            Icon(imageVector = Icons.Default.CheckCircleOutline,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Green)
        }
        else{
            Icon(imageVector = Icons.Default.Block,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Red)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(id = R.string.pass_strength),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold)
    }

    //pass matches
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically)
    {
        if (doPassMatches)
        {
            Icon(imageVector = Icons.Default.CheckCircleOutline,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Green)
        }
        else{
            Icon(imageVector = Icons.Default.Block,
                contentDescription = stringResource(id = R.string.icon_descr),
                tint = Color.Red)
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(id = R.string.pass_match),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold)
    }
}
}