package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.EnButton
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.SignInPasswordTextField
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.components.PassStrengthTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePassScreen(
    viewModel: CreatePassViewModel,
    navController: NavController,
    args:String
)
{
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            event->
            when(event)
            {
                is CreatePassViewModel.CreatePassUiEvent.OnBackBtnClick->{
                    navController.navigateUp()
                }
                is CreatePassViewModel.CreatePassUiEvent.OnContinueBtnClick->{
                    navController.navigate(Screens.UserNameScreen(validatedEmail = args, validatedPass = viewModel.inputPassword))
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
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.ic_icon),
                                    contentDescription = stringResource(id = R.string.image_descr),
                                    modifier = Modifier.size(86.dp)
                                )
                            }//row
                        }, //title
                            navigationIcon ={
                                IconButton(onClick = {
                                    viewModel.onEvent(CreatePassEvent.OnBackBtnClick)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBackIosNew,
                                        contentDescription = stringResource(
                                            id = R.string.NavIcon_descr
                                        ))
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = colorResource(id = R.color.bar_color)
                            )
                        )

                    }
                ) {
                        innerPadding->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    )
                    {

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

                        SignInPasswordTextField(
                            showPassword = viewModel.showPassResult.showPass,
                            value = viewModel.inputPassword,
                            label = "Create password",
                            isError = false,
                            icon = viewModel.showPassResult.icon,
                            onValueChange = {
                                value->
                                viewModel.onEvent(CreatePassEvent.PassInput(value))
                            }
                        ) {
                            viewModel.onEvent(CreatePassEvent.OnShowPassClick)
                        }
                        PassStrengthTextField(
                            text = stringResource(id = R.string.pass_chars),
                            textColor =viewModel.createdPassValidationResult.passLengthColor ,
                            iconColor = viewModel.createdPassValidationResult.passLengthIconColor,
                            imageVector = viewModel.createdPassValidationResult.passLengthIcon)

                        PassStrengthTextField(
                            text = stringResource(id = R.string.pass_container),
                            textColor =viewModel.createdPassValidationResult.hasNumberAndUpperColor,
                            iconColor = viewModel.createdPassValidationResult.hasNumberAndUpperIconColor,
                            imageVector = viewModel.createdPassValidationResult.hasNumberAndUpperCaseIcon)
                        PassStrengthTextField(
                            text = stringResource(id = R.string.pass_strength),
                            textColor =viewModel.createdPassValidationResult.strongPassColor,
                            iconColor = viewModel.createdPassValidationResult.strongPassIconColor,
                            imageVector = viewModel.createdPassValidationResult.strongPassIcon)
                        Spacer(modifier = Modifier.height(4.dp))
                        SignInPasswordTextField(
                            showPassword = viewModel.showPassResult.showPass,
                            value = viewModel.confirmPassInput,
                            label = "Confirm password",
                            isError = false,
                            icon = viewModel.showPassResult.icon,
                            onValueChange = {
                                value->
                                viewModel.onEvent(CreatePassEvent.ConfirmPassInput(value))}
                        ) {
                            viewModel.onEvent(CreatePassEvent.OnShowPassClick)
                        }
                        PassStrengthTextField(text = stringResource(
                            id = R.string.pass_match),
                            textColor =viewModel.passMatchingResult.passMatchColor,
                            iconColor = viewModel.passMatchingResult.passMatchIconColor,
                            imageVector = viewModel.passMatchingResult.passMatchIcon)
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically){
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                       
                        Spacer(modifier = Modifier.height(28.dp))

                        EnButton(
                            onButtonClick = {
                            viewModel.onEvent(CreatePassEvent.OnContinueBtnClick)
                            },
                            btnEnabled = viewModel.passMatchingResult.arePasswordsValid,
                            text = stringResource(
                            id = R.string.continue_btn
                        ))
                    }
                }
}