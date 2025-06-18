package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.AuthenticationLottie
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.PickCompanyAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    viewModel: LoginViewModel,
    navController: NavController,
)
{
     val context= LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(LoginScreenEvent.GetRememberedUser)
        viewModel.onEvent(LoginScreenEvent.OnFetchRegisteredCompanies)


        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is LoginViewModel.LoginScreenUiEvent.OnNavigateTo->{
                    navController.navigate(event.screen)
                }
                is LoginViewModel.LoginScreenUiEvent.OnOwnerEmailScreen->{
                    navController.navigate(Screens.CreateOwnerEmailScreen)
                }
                is LoginViewModel.LoginScreenUiEvent.OnShowToast->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is LoginViewModel.LoginScreenUiEvent.OnNavigateToHomeScreen->{
                    navController.navigate(Screens.HomeScreen(companyID = viewModel.user.value.companyID, userID = viewModel.userID.value))
                }
                is LoginViewModel.LoginScreenUiEvent.OnNavigateToJoinCompanyScreen->{
                    navController.navigate(Screens.JoinSelectCompanyScreen)
                }
                is LoginViewModel.LoginScreenUiEvent.OnCountDown->{
                    if (viewModel.countDownTimer.value>0)
                    {
                        delay(1000)
                        viewModel.onEvent(LoginScreenEvent.OnCountDown)

                    }

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
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = stringResource(id = R.string.SignIn_title),
                                color = colorResource(id = R.color.text_color),
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
            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center)
            {
                item {
AuthenticationLottie()

                }

                item {
    //Email
     OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
         colors = OutlinedTextFieldDefaults.colors(
             focusedBorderColor = colorResource(id = R.color.btn_color),
             focusedLabelColor = colorResource(id = R.color.btn_color)),
        value = viewModel.user.value.email,
        onValueChange = {email->
            viewModel.onEvent(LoginScreenEvent.OnEmailChange(email))
            if (viewModel.user.value.rememberMe)
            {
                viewModel.onEvent(LoginScreenEvent.OnRememberUser)
            }
                       },
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
}

                //Password
                    item {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = colorResource(id = R.color.btn_color),
                                focusedLabelColor = colorResource(id = R.color.btn_color)),
                            value =viewModel.user.value.password,
                            onValueChange ={pass->viewModel.onEvent(LoginScreenEvent.OnPassChange(pass))
                                if (viewModel.user.value.rememberMe)
                                {
                                    viewModel.onEvent(LoginScreenEvent.OnRememberUser)
                                }
                                           },
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
                    }
                //select company
                item {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.btn_color)),
                        isError = viewModel.isError.value,
                        value = viewModel.user.value.companyName,
                        readOnly = true,
                        onValueChange ={ },
                        supportingText = { Text(text = stringResource(id = R.string.select_company_supporting_txt)) },
                        shape = RoundedCornerShape(16.dp),
                        placeholder = { Text(text = stringResource(id = R.string.select_company))},
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = {viewModel.onEvent(LoginScreenEvent.OnSelectCompanyClick) })
                            {
                                Icon(
                                    imageVector = Icons.Default.ArrowForwardIos,
                                    contentDescription = stringResource(id = R.string.icon_descr)
                                )
                            }
                        }
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp, 4.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = viewModel.user.value.rememberMe,
                            onCheckedChange ={viewModel.onEvent(LoginScreenEvent.OnCheckedChange)},
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = Color.White,
                                checkedColor = colorResource(id = R.color.btn_color)
                                ))
                        Text(text = stringResource(id = R.string.remember_me))
                    }
                }
                //error message
                item {
                    if (viewModel.isError.value || viewModel.unverifiedEmailErr.value)
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
                }
                item {
                    if (viewModel.showResendBtn.value)
                    {

                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable(enabled = viewModel.enableResendBtn.value)
                                {
                                    viewModel.onEvent(LoginScreenEvent.OnSendVerificationEmail)
                                }) {
                                Icon(imageVector = Icons.Default.Refresh,
                                    contentDescription = stringResource(id = R.string.icon_descr) )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(text = "${stringResource(id = R.string.resend)} ${viewModel.countDownTimer.value}")
                            }

                    }
                }
//sign in btn
                item {
                    if (viewModel.showProgressBar.value)
                    {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp) ,
                            color = colorResource(id = R.color.bar_color)
                        )
                    }
                    else
                    {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors =ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.btn_color)),
                            onClick = { viewModel.onEvent(LoginScreenEvent.OnSignInClick)},
                        ) {
                            Text(
                                text = stringResource(id = R.string.SignIn_title),
                                color = colorResource(id =R.color.text_color),
                                fontSize = 18.sp)
                        }
                    }

                }
                item {
                    TextButton(onClick = { viewModel.onEvent(LoginScreenEvent.OnJoinCompanyClick)  },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorResource(id = R.color.btn_color),
                            containerColor = Color.Transparent)) {
                        Text(text = stringResource(id = R.string.create_acc_txt))
                    }
                }
                item {
                    TextButton(onClick = {
                        viewModel.onEvent(LoginScreenEvent.OnRegisterCompanyClick) },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorResource(id = R.color.btn_color),
                            containerColor = Color.Transparent)) {
                        Text(text = stringResource(id = R.string.register_company_txt))
                    }
                }

                item {
                    Text(text = stringResource(id = R.string.support_txt),
                        fontSize = 10.sp)
                }

            }
        }
        if (viewModel.showDialog.value)
        {
           PickCompanyAlertDialog(
                companies = viewModel.registeredCompanies.value,
               onDismissRequest = {viewModel.onEvent(LoginScreenEvent.OnCancelDialogClick)},
               query = viewModel.companyQuery.value,
               onQueryChange = {query-> viewModel.onEvent(LoginScreenEvent.OnCompanySearch(query = query))},
               filteredCompanies = viewModel.filteredCompanies.value,
               onCompanyClick ={
                   selectedCompany-> viewModel.onEvent(LoginScreenEvent.OnCompanyClick(selectedCompany))
                   if (viewModel.user.value.rememberMe)
                   {
                       viewModel.onEvent(LoginScreenEvent.OnRememberUser)
                   }}
           )
        }
    }
}