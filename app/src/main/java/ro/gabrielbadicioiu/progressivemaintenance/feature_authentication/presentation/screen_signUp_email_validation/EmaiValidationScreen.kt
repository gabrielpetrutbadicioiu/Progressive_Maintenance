package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EmailTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EnButton
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.util.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.components.TermsText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailValidationScreen(
viewModel: EmailValidationViewModel,
navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            event->
            when(event)
            {
                is EmailValidationViewModel.EmailValidUiEvent.OnBackBtnClick->{
                    navController.navigateUp()
                }
                is EmailValidationViewModel.EmailValidUiEvent.OnContinueBtnClick->{
                    navController.navigate(Screens.OTPScreen)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
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
                        style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.width(20.dp))
                }//row
            }, //title
                navigationIcon ={
                    IconButton(onClick = { viewModel.onEvent(EmailValidationEvent.OnBackBtnClick) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = stringResource(
                            id = R.string.NavIcon_descr
                        ))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
         )

        }

    )//scaffold
    {
        innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = stringResource(id = R.string.image_description),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp)
            )
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp))
            EmailTextField(value = viewModel.enteredEmail,
                label = viewModel.validationResult.labelMessage,
                isError =viewModel.validationResult.isError/*TODO*/ ) {
                email->
                viewModel.onEvent(EmailValidationEvent.EnteredEmail(email))
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp))
            TermsText()
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
            EnButton({viewModel.onEvent(EmailValidationEvent.OnNextClick)},
                viewModel.enteredEmail.isNotBlank(),
                stringResource(
                id = R.string.continue_btn
            ))

        }//column
//TODO de implementat viewmodel si vezi daca poti sa faci scrisu ala sa apara sters cum in clipu ala cu tiktok
    }
}