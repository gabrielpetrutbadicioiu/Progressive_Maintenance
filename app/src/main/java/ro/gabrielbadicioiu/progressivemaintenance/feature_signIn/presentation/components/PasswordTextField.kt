package ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ro.gabrielbadicioiu.progressivemaintenance.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    showPassword:Boolean,
    value:String,
    onValueChange:(String)->Unit,
                      )
{
    Box {
        //email
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor =  MaterialTheme.colorScheme.primary
            ),
            singleLine = true,
            label = { Text(text = stringResource(R.string.password_hint))},
            leadingIcon = {Icon(imageVector = Icons.Default.Lock,
                contentDescription = stringResource(
                    id = R.string.password_hint
                ) )},

            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            value = value,
            onValueChange ={onValueChange(it)} )
    }
}

