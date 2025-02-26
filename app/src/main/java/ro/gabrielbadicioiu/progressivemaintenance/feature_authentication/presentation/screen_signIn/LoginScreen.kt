package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    viewModel: LoginViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(LoginScreenEvent.GetRememberedUser)
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is LoginViewModel.LoginScreenUiEvent.OnNavigateTo->{
                    navController.navigate(event.screen)
                }
                is LoginViewModel.LoginScreenUiEvent.OnOwnerEmailScreen->{
                    navController.navigate(Screens.CreateOwnerEmailScreen)
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_icon),
                                contentDescription = stringResource(id = R.string.image_descr),
                                modifier = Modifier.size(86.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.bar_color),
                        scrolledContainerColor = colorResource(id = R.color.scroll_bar_color)
                    )
                )
            },
        )
        { innerPadding ->
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
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
                //Email
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.user.value.email,
                    onValueChange = {email-> viewModel.onEvent(LoginScreenEvent.OnEmailChange(email))},
                    label ={
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            if (viewModel.isError.value)
                            {
                                Icon(imageVector = Icons.Default.WarningAmber,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    )
                            }
                            Text(text = stringResource(id = R.string.email_hint))
                        }
                    } ,
                    isError = viewModel.isError.value,
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    )

                //Password
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value =viewModel.user.value.password,
                    onValueChange ={pass->viewModel.onEvent(LoginScreenEvent.OnPassChange(pass))},
                    singleLine = true,
                    isError = viewModel.isError.value,
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            if (viewModel.isError.value)
                            {
                                Icon(imageVector = Icons.Default.WarningAmber,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                )
                            }
                            Text(text = stringResource(id = R.string.password_hint))
                        }
                    },
                    leadingIcon = {
                        Icon(imageVector =Icons.Default.Lock,
                            contentDescription = stringResource(id = R.string.icon_descr) )
                    },
                    trailingIcon = {
                        IconButton(onClick = {viewModel.onEvent(LoginScreenEvent.OnShowPassClick)}) {
                            Icon(imageVector = if(viewModel.showPassword.value) Icons.Default.Visibility else Icons.Default.VisibilityOff ,
                                contentDescription = stringResource(id = R.string.icon_descr) )}
                        },
                    visualTransformation =if(viewModel.showPassword.value) VisualTransformation.None else PasswordVisualTransformation())

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp, 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = viewModel.user.value.rememberMe,
                        onCheckedChange ={viewModel.onEvent(LoginScreenEvent.OnCheckedChange)}, )
                    Text(text = stringResource(id = R.string.remember_me))
                }
                if (viewModel.isError.value)
                {
                    IconTextField(
                        text = viewModel.errorMessage.value,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red,
                        iconSize = 24,
                        textSize = 16,
                        clickEn = viewModel.clickableErr.value
                    ) {
                        viewModel.onEvent(LoginScreenEvent.OnSendVerificationEmail)
                    }
                }
                
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors =ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.bar_color)),
                onClick = { viewModel.onEvent(LoginScreenEvent.OnSignInClick)},
                ) {
                Text(
                    text = stringResource(id = R.string.SignIn_title),
                    color = colorResource(id =R.color.text_color),
                    fontSize = 18.sp)
            }
                
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = stringResource(id = R.string.create_acc_txt))
                }
                TextButton(onClick = {

                    viewModel.onEvent(LoginScreenEvent.OnRegisterCompanyClick) }) {
                    Text(text = stringResource(id = R.string.register_company_txt))
                }
                Text(text = stringResource(id = R.string.support_txt),
                    fontSize = 10.sp)
            }
        }
    }
}