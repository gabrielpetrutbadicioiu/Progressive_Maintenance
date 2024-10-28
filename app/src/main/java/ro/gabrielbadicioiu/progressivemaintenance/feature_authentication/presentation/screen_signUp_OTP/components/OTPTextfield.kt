package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R


@Composable
fun OTPTextField(
    value:String,
    onValueChange:(String)->Unit,
    onOTPComplete:()->Unit,
    isError:Boolean
)
{
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        OutlinedTextField(
            modifier = Modifier
                .wrapContentSize(),
            value =value ,
            onValueChange ={
                otp->
                if(otp.length<=6)
                { onValueChange(otp)}
                if (otp.length>=6)
                {
                    onOTPComplete()
                }
                           },
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Blue,
                fontWeight = FontWeight.Bold
            ),
            label = {
                Text( text =
                if (isError)stringResource(id = R.string.invalid_otp) else {
                    stringResource(id = R.string.verification_code)})},
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

        )
    }
}