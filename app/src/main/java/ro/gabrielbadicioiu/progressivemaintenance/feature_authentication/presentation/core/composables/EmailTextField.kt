package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField(value:String,
                   label:String,
                 //  labelIcon:ImageVector,
                   isError:Boolean,
                   onValueChange:(String)->Unit)
{
Box {
    //email
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
           ,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor =  MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        label = 
        {  Row(
            modifier = Modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isError)
            {
                Icon(imageVector = Icons.Default.WarningAmber,
                    contentDescription = stringResource(id = R.string.warning_icon_descr),
                    modifier = Modifier.size(16.dp))
            }

            Text(text = label)
        }//row
        },//label
        isError = isError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        leadingIcon = {Icon(imageVector = Icons.Default.Email,
            contentDescription = stringResource(
            id = R.string.email_hint
        ) )},
        value = value,
        onValueChange ={
            email->
            onValueChange(email)} )
}
}