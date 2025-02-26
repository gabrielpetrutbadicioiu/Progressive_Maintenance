package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.Composables.ExpandableText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOwnerEmailScreen(
    viewModel: CreateOwnerEmailViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {event->
            when(event)
            {
                is CreateOwnerEmailViewModel.CreateOwnerAccountUiEvent.OnNavigateUp->{
                    navController.navigateUp()
                }
                is CreateOwnerEmailViewModel.CreateOwnerAccountUiEvent.NavigateToOwnerPass->{
                    navController.navigate(Screens.CreateOwnerPassScreen(email = event.email, poppedBackStack = false))
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

                    },
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.onEvent(CreateOwnerEmailEvent.OnNavigateUp)}) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.SignIn_title),
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
                Image(
                    painter = painterResource(id = R.drawable.auth_image),
                    contentDescription = stringResource(
                        id = R.string.image_description
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp)
                )
                ExpandableText(
                    shortText = stringResource(id = R.string.short_text) ,
                    fullText = stringResource(id = R.string.full_text),
                    isExpanded = viewModel.isExpanded.value,
                    onExpandClick = {viewModel.onEvent(CreateOwnerEmailEvent.OnExpandTextClick)})
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.inputEmail.value,
                    onValueChange = {email-> viewModel.onEvent(CreateOwnerEmailEvent.OnEmailChange(email))},
                    placeholder = { Text(text = stringResource(id = R.string.email_hint))},
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    supportingText = { Text(text = stringResource(id = R.string.email_recommendation))},
                    isError = viewModel.isErr.value
                )
                if(viewModel.isErr.value)
                {
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically){

                        Icon(imageVector =Icons.Default.WarningAmber ,
                            contentDescription = stringResource(id = R.string.icon_descr),
                            tint = Color.Red,
                            modifier = Modifier.padding(4.dp))
                        Text(text = stringResource(id = R.string.email_format_err),
                            color = Color.Red,
                            fontSize = 14.sp)
                    }

                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Button(
                    onClick = {viewModel.onEvent(CreateOwnerEmailEvent.OnContinueBtnClick) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    enabled = viewModel.inputEmail.value.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                ) {
                    Text(text = stringResource(id = R.string.continue_btn))
                }
            }
        }
    }
}
