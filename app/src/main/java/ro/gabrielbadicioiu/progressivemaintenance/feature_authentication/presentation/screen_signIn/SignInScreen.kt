package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EmailTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.CreateAccTxtBtn
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.RememberMe
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.SignInButton
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.SignInPasswordTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.util.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    viewModel:SignInViewModel,
    navController: NavController
)
{
    // TODO de implementat validare email si parola cu erori si tot ce tb
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
                event->
            when(event)
            {
                is SignInViewModel.UiEvent.SignUp->
                {
                    navController.navigate(Screens.EmailValidationScreen)
                }
                is  SignInViewModel.UiEvent.showToast->
                {

                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.SignIn_title),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary))

        }
    ) {
            innerPadding->
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
                    .padding(16.dp, 0.dp))

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))

            EmailTextField(
                value = viewModel.emailInput,
                label = stringResource(id = R.string.email_hint),
                //labelIcon = Icons.Default.NewLabel,
                isError = false) {
                email->
              viewModel.onEvent(SignInScreenEvent.EnteredEmail(email))
            }
            SignInPasswordTextField(
                showPassword = viewModel.showPassResult.showPass,
                value = viewModel.passInput,
                label = stringResource(id = R.string.password_hint),
                isError = false ,
                icon = viewModel.showPassResult.icon,
                onValueChange = {
                    password->
                    viewModel.onEvent(SignInScreenEvent.EnteredPassword(password))
                }) {
                viewModel.onEvent(SignInScreenEvent.OnShowPasswordClick)
            }
            RememberMe(checked =viewModel.rememberMeChecked) {
                viewModel.onEvent(SignInScreenEvent.OnRememberMeCheck)
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
            SignInButton({}, enable = viewModel.emailInput.isNotBlank() && viewModel.passInput.isNotBlank())
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp))
            CreateAccTxtBtn {
                viewModel.onEvent(SignInScreenEvent.OnCreateAccClick)
            }
        }

    }

}