package ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation

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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components.CreateAccTxtBtn
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components.EmailTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components.PasswordTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.components.SignInButton
import ro.gabrielbadicioiu.progressivemaintenance.util.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
)
{
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            event->
            when(event)
            {
                is LoginViewModel.UiEvent.SignUp->
                {
                    navController.navigate(Screens.SignUpScreen)
                }
                is  LoginViewModel.UiEvent.showToast->
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

            EmailTextField(value = "") {
                /*TODO*/
            }
            PasswordTextField(
                showPassword = viewModel.showPasswordChecked,
                value = "") {
                /*TODO*/
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))
            SignInButton {
                /*TODO*/
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp))
            CreateAccTxtBtn {
                viewModel.onEvent(LogInScreenEvent.onCreateAccClick)
            }
        }

    }


}