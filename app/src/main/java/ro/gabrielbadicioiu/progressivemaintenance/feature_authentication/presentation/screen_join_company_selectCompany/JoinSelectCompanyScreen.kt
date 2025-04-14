package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_join_company_selectCompany

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.AuthenticationLottie
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.PickCompanyAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinSelectCompanyScreen(
    viewModel: JoinSelectCompanyViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(JoinSelectCompanyEvent.OnFetchRegisteredCompanies)
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is JoinSelectCompanyViewModel.JoinSelectCompanyUiEvent.OnContinueClick->{
                    navController.navigate(Screens.JoinCompanyUserPassword(
                        email = viewModel.registrationEmail.value,
                        companyID = viewModel.selectedCompany.value.id,
                        hasPoppedBackStack = false))
                }
                is JoinSelectCompanyViewModel.JoinSelectCompanyUiEvent.OnNavigateUp->{
                    navController.navigateUp()
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
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                viewModel.onEvent(JoinSelectCompanyEvent.OnNavigateUp)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.SignIn_title),
                                color = colorResource(id = R.color.text_color))
                        }
                    },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            Image(
//                                painter = painterResource(id = R.drawable.ic_icon),
//                                contentDescription = stringResource(id = R.string.image_descr),
//                                modifier = Modifier.size(86.dp)
//                            )
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

                //email
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.registrationEmail.value,
                    onValueChange = {value->viewModel.onEvent(JoinSelectCompanyEvent.OnRegistrationEmailChange(value))},
                    placeholder = { Text(text = stringResource(id = R.string.email_hint)) },
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    supportingText = { Text(text = stringResource(id = R.string.email_recommendation)) },
                    isError = !viewModel.emailMatchesPattern.value&&viewModel.registrationEmail.value.isNotBlank()
                    
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                )
                //select company
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    isError = false,
                    value = viewModel.selectedCompany.value.organisationName,
                    readOnly = true,
                    onValueChange ={ },
                    supportingText = { Text(text = stringResource(id = R.string.select_company_supporting_txt)) },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.select_company))},
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick ={viewModel.onEvent(JoinSelectCompanyEvent.OnShowDialogClick)})
                        {
                            Icon(
                                imageVector = Icons.Default.ArrowForwardIos,
                                contentDescription = stringResource(id = R.string.icon_descr)
                            )
                        }
                    }
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                )
                //otp
                if (viewModel.selectedCompany.value.organisationName.isNotEmpty())
                {
                    OutlinedTextField(
                        value =viewModel.enteredOTP.value,
                        onValueChange ={otpValue->
                            if (otpValue.length<=6)
                            {
                                viewModel.onEvent(JoinSelectCompanyEvent.OnOtpValueChange(otpValue))
                            }},
                        modifier = Modifier.width(250.dp),
                        leadingIcon = { Icon(imageVector = Icons.Default.Key,
                            contentDescription = stringResource(id = R.string.icon_descr))},
                        placeholder = { Text(text = stringResource(id = R.string.otp_hint))},
                        supportingText = { Text(text = stringResource(id = R.string.get_otp_hint))},
                        textStyle = TextStyle(fontSize = 24.sp, letterSpacing = 12.sp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        shape = RoundedCornerShape(12.dp),
                        isError = viewModel.isError.value
                        )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                )
                //error
                if (viewModel.isError.value)
                {
                    IconTextField(
                        text = viewModel.errorMessage.value,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red,
                        iconSize = 24,
                        textSize = 16,
                        clickEn = false
                    ) {}
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                )

                //continue btn
                Button(
                    onClick = {viewModel.onEvent(JoinSelectCompanyEvent.OnContinueBtnClick)},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    enabled = viewModel.emailMatchesPattern.value && viewModel.selectedCompany.value.organisationName.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.btn_color))
                ) {
                    Text(text = stringResource(id = R.string.continue_btn))
                }

            }
        }
        if (viewModel.showDialog.value)
        {
            PickCompanyAlertDialog(
                onDismissRequest = {viewModel.onEvent(JoinSelectCompanyEvent.OnDialogDismiss)  },
                companies = viewModel.registeredCompanies.value,
                filteredCompanies =viewModel.filteredCompanies.value ,
                query = viewModel.companyQuery.value,
                onQueryChange ={query->viewModel.onEvent(JoinSelectCompanyEvent.OnCompanySearch(query))}
            ) {selectedCompany->
                viewModel.onEvent(JoinSelectCompanyEvent.OnCompanyClick(selectedCompany))
            }
        }

    }
}
