package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EmailTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.CreateAccTxtBtn
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.RememberMe
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.SignInButton
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.SignInPasswordTextField
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.IconTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    viewModel:SignInViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SignInViewModel.UiEvent.SignUp -> {
                    navController.navigate(Screens.EmailValidationScreen)
                }

                is SignInViewModel.UiEvent.ShowToast -> {

                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                is SignInViewModel.UiEvent.SignIn -> {
                    navController.navigate(Screens.HomeScreen)
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    )
    { Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "IEPM", style = MaterialTheme.typography.titleLarge)
                        Image(
                            painter = painterResource(id = R.drawable.ic_icon),
                            contentDescription = stringResource(id = R.string.image_descr),
                            modifier = Modifier.size(86.dp)
                        )

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.bar_color))
            )//top app bar

        }
    )//scaffold
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = stringResource(
                    id = R.string.image_description
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            EmailTextField(
                value = viewModel.emailInput,
                label = stringResource(id = R.string.email_hint),
                isError = viewModel.authResult.isError
            ) { email ->
                viewModel.onEvent(SignInScreenEvent.EnteredEmail(email))
            }
            SignInPasswordTextField(
                showPassword = viewModel.showPassResult.showPass,
                value = viewModel.passInput,
                label = stringResource(id = R.string.password_hint),
                isError = viewModel.authResult.isError,
                icon = viewModel.showPassResult.icon,
                onValueChange = { password ->
                    viewModel.onEvent(SignInScreenEvent.EnteredPassword(password))
                }) {
                viewModel.onEvent(SignInScreenEvent.OnShowPasswordClick)
            }

            RememberMe(checked = viewModel.rememberedUser.rememberMe) {
                viewModel.onEvent(SignInScreenEvent.OnRememberMeCheck)
            }

            if (viewModel.authResult.isError) {
                IconTextField(
                    text = viewModel.authResult.errorMessage.toString(),
                    icon = Icons.Default.WarningAmber,
                    color = Color.Red,
                    iconSize = 25,
                    textSize = 15,
                    clickEn = false,
                    onClick = {}
                )
            } else if (viewModel.authResult.isEmailVerified == false) {
                IconTextField(
                    text = viewModel.verifyEmailTxt,
                    icon = viewModel.verifyEmailIcon,
                    color = viewModel.verifyEmailTxtColor,
                    iconSize = 25,
                    textSize = 15,
                    clickEn = true
                ) {
                    viewModel.onEvent(SignInScreenEvent.OnSendVerificationEmail)
                }
            } else {
                Text(text = "")
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            SignInButton({
                viewModel.onEvent(SignInScreenEvent.OnSignInBtnClick)
            }, enable = viewModel.emailInput.isNotBlank() && viewModel.passInput.isNotBlank())
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
            CreateAccTxtBtn {
                viewModel.onEvent(SignInScreenEvent.OnCreateAccClick)
            }
        }
    }//scaffold
}

    }

