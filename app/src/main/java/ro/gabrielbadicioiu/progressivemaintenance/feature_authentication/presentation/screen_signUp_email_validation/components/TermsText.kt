package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import ro.gabrielbadicioiu.progressivemaintenance.R

@Composable
fun TermsText()
{
    Row (modifier = Modifier
        .fillMaxWidth()){
        Text(
            text = buildAnnotatedString {
            append(stringResource(id = R.string.terms_conditions_first))

                withStyle(
                    style = SpanStyle(
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic
                    ))
                    {
                        append(stringResource(id = R.string.application_name))
                    }
                withStyle(
                    style = SpanStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                {
                    append(stringResource(id = R.string.terms_conditions_second))
                }
                append(stringResource(id = R.string.terms_conditions_third))
                withStyle(
                    style = SpanStyle(
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic
                    ))
                {
                    append(stringResource(id = R.string.application_name))
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                {
                    append(stringResource(id = R.string.terms_conditions_fourth))
                }


            },
            fontSize = 11.sp

        )

    }
}