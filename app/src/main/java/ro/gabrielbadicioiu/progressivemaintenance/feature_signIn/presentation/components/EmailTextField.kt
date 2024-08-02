package ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(value:String, onValueChange:(String)->Unit)
{
Box {
    //email
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
           /* .border(
                BorderStroke(width = 2.dp, color = Purple40),
                shape = RoundedCornerShape(50)
            )*/,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor =  MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        label = { Text(text = stringResource(R.string.email_hint))},
        leadingIcon = {Icon(imageVector = Icons.Default.Email,
            contentDescription = stringResource(
            id = R.string.email_hint
        ) )},
        value = value,
        onValueChange ={onValueChange(it)} )
}
}