package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.AuthenticationLottie
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.PasswordRequirements

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOwnerPassScreen(
    poppedBackStack:Boolean,
    email:String,
    viewModel: CreateOwnerPassViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
       viewModel.eventFlow.collectLatest { event->
           when(event)
           {
               is CreateOwnerPassViewModel.CreateOwnerPassUiEvent.OnNavigateUp->{
                   navController.navigateUp()
               }
               is CreateOwnerPassViewModel.CreateOwnerPassUiEvent.OnContinueBtnClick->{
                   navController.navigate(Screens.CompanyDetailsScreen(
                       selectedCountry = "",
                       userID = event.currentFirebaseUser?.uid,
                       userEmail = event.currentFirebaseUser?.email
                   ))
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
                    title = {},
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                viewModel.onEvent(CreateOwnerPassEvent.OnNavigateUp)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.email_hint),
                                color = colorResource(id = R.color.text_color))
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
                AuthenticationLottie()
//                Image(
//                    painter = painterResource(id = R.drawable.auth_image),
//                    contentDescription = stringResource(
//                        id = R.string.image_description
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp, 0.dp)
//                )
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                )
                //password
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value =viewModel.password.value,
                    onValueChange ={pass-> if (pass.length<=20) viewModel.onEvent(CreateOwnerPassEvent.OnPassChange(pass))},
                    singleLine = true,
                    isError = false,
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(text = stringResource(id = R.string.password_hint))
                        }
                    },
                    leadingIcon = {
                        Icon(imageVector =Icons.Default.Lock,
                            contentDescription = stringResource(id = R.string.icon_descr) )
                    },
                    trailingIcon = {
                        IconButton(onClick = {viewModel.onEvent(CreateOwnerPassEvent.OnShowPassClick)}) {
                            Icon(imageVector = if(viewModel.showPass.value) Icons.Default.Visibility else Icons.Default.VisibilityOff ,
                                contentDescription = stringResource(id = R.string.icon_descr) )}
                    },
                    visualTransformation =if(viewModel.showPass.value) VisualTransformation.None else PasswordVisualTransformation()
                )
                //confirm password
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value =viewModel.confPass.value,
                    onValueChange ={confPass-> if (confPass.length<=20) viewModel.onEvent(CreateOwnerPassEvent.OnConfPassChange(confPass))},
                    singleLine = true,
                    isError = false,
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(text = stringResource(id = R.string.confirm_password))
                        }
                    },
                    leadingIcon = {
                        Icon(imageVector =Icons.Default.Lock,
                            contentDescription = stringResource(id = R.string.icon_descr) )
                    },
                    trailingIcon = {
                        IconButton(onClick = {viewModel.onEvent(CreateOwnerPassEvent.OnShowConfPassClick)}) {
                            Icon(imageVector = if(viewModel.showConfPass.value) Icons.Default.Visibility else Icons.Default.VisibilityOff ,
                                contentDescription = stringResource(id = R.string.icon_descr) )}
                    },
                    visualTransformation =if(viewModel.showConfPass.value) VisualTransformation.None else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordRequirements(password = viewModel.password.value, confPassword =viewModel.confPass.value)
                if (viewModel.isError.value) {
                    IconTextField(
                        text = viewModel.errMsg.value,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red,
                        iconSize = 24,
                        textSize = 16,
                        clickEn = false
                    ) {}
                }
                    val enable=viewModel.password.value.length>8 && viewModel.password.value== viewModel.confPass.value && viewModel.password.value.isNotEmpty() &&viewModel.password.value.matches(Regex(".*[A-Z].*")) && viewModel.password.value.matches(Regex(".*\\d.*"))
                if (viewModel.showProgressBar.value)
                {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp) ,
                        color = colorResource(id = R.color.bar_color)
                    )
                }
                else{
                    Button(
                        onClick = {
                            viewModel.onEvent(CreateOwnerPassEvent.OnContinueBtnClick(
                                email=email,
                                pass = viewModel.password.value,
                                poppedBackStack = poppedBackStack
                            ))

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        enabled = enable,
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                    ) {
                        Text(text = stringResource(id = R.string.continue_btn))
                    }
                }

            }
        }
    }
}
