@file:Suppress("IMPLICIT_CAST_TO_ANY")

package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.components.OTPInput

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPScreen(viewModel: OTPViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.SignUp_title),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                    }//row
                }, //title
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(
                                id = R.string.NavIcon_descr
                            )
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            LaunchedEffect(key1 = true) {
                if (viewModel.countDownValue > 0) {
                    viewModel.onEvent(OTPEvent.startTimer)
                } else {
                    cancel()
                }


            }
            LaunchedEffect(key1 = true) {
                if (viewModel.showIndicator) {
                    delay(500L)
                    viewModel.onEvent(OTPEvent.onResendOTPClick)
                }

            }

            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = stringResource(id = R.string.image_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            Text(
                text = stringResource(id = R.string.check_email_txt),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
            Text(text = stringResource(id = R.string.verification_prompt))

            OTPInput(otpLength = 6) {

            }

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable(enabled = viewModel.hasTimeExpired) {
                        viewModel.onEvent(OTPEvent.onResendOTPClick)

                    },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewModel.showIndicator) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 1.dp
                    )
                } else {
                    if (viewModel.hasTimeExpired) {
                        Text(
                            text = stringResource(id = R.string.resend_otp),
                            color = Color.Red,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Icon(
                            imageVector = Icons.Default.Restore,
                            contentDescription = stringResource(id = R.string.resend_icon),
                            modifier = Modifier.size(18.dp),
                            tint = Color.Red
                        )
                    } else {
                        Text(text = "${stringResource(id = R.string.resend)} ${viewModel.countDownValue}")
                    }
                }


                /*
                 if (viewModel.resendProgress)
                 {

                 }
                 else{
                     Text(
                         text = stringResource(id = "R.string.resend_otp),
                         fontWeight = if (viewModel.resendCode) { FontWeight.ExtraBold} else {FontWeight.Normal},
                         color = if (viewModel.resendCode) {Color.Red} else {Color.White}
                     )
                     Spacer(modifier = Modifier.width(4.dp))
                     if (!viewModel.resendCode)
                     {
                         Text(text = "${viewModel.countDownTimer}")
                     }
                     else
                     {
                         Icon(
                             imageVector = Icons.Default.Replay,
                             contentDescription = stringResource(id = R.string.resend_icon),
                             tint = Color.Red)
                     }
                 }*/


            }


        }

    }

}