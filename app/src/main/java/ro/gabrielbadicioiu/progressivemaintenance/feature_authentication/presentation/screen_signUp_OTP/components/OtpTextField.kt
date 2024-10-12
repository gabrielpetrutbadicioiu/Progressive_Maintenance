package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OtpTextField(
    otpValue:String,
    onValueChange:(String)->Unit
)
{
    Box(modifier = Modifier.fillMaxWidth(),)
    {
        BasicTextField(
            decorationBox = {
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(6){
                        index->
                        val char=when{
                            index>=otpValue.length->""
                            else->otpValue[index].toString()
                        }
                        Text(
                            modifier = Modifier
                                .width(40.dp)
                                .border(
                                    1.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(8.dp)

                                )
                                .padding(8.dp),
                            text = char,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            },
            value =otpValue ,
            onValueChange = {enteredValue->

                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
    }
}