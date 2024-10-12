package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OTPInput(
    otpLength: Int = 6,  // Lungimea OTP-ului, de obicei 4 sau 6 cifre
    onOtpComplete: (String) -> Unit  // Callback ce se apelează când OTP-ul este complet

) {
    // Stare pentru a stoca valoarea fiecărui câmp de input
    val otpValues = remember { mutableStateListOf(*Array(otpLength) { "" }) }
    // Referințe pentru a muta focusul între câmpurile de input
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Iterăm pentru fiecare câmp din OTP
        for (i in 0 until otpLength) {
            OutlinedTextField(
                value = otpValues[i],
                onValueChange = { value ->
                    if (value.length <= 1 && value.all { it.isDigit() }) {
                        // Actualizăm valoarea câmpului curent
                        otpValues[i] = value
                        // Mutăm focusul la următorul câmp dacă există un caracter
                        if (value.isNotEmpty() && i < otpLength - 1) {
                            focusRequesters[i + 1].requestFocus()
                        }
                        // Dacă OTP-ul este complet, apelăm callback-ul
                        if (otpValues.all { it.isNotEmpty() }) {
                            onOtpComplete(otpValues.joinToString(""))
                        }
                    }
                },
                modifier = Modifier
                    .size(55.dp)  // Dimensiunea fiecărui câmp
                    .focusRequester(focusRequesters[i])
                    .padding(4.dp),  // Spațiu între câmpuri
                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.Center),
                singleLine = true,  // Fiecare câmp să fie pe o singură linie
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),  // Doar cifre
                maxLines = 1
            )

            // Focusarea automată la primul câmp
            LaunchedEffect(Unit) {
                if (i == 0) {
                    focusRequesters[i].requestFocus()
                }
            }
        }
    }
}
