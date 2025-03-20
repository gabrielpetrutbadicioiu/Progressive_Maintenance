package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserProfile


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.Work
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinCompanyUserProfileScreen(
    companyID:String,
    userID:String,
    email:String,
    navController: NavController,
    viewModel: CreateUserProfileViewModel
)
{
    val photoPickerLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            uri->
        viewModel.onEvent(CreateUserProfileScreenEvent.OnUriResult(uri))
    }
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(CreateUserProfileScreenEvent.OnGetCurrentUser(userID = userID, email=email))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is CreateUserProfileViewModel.CreateUserProfileUiEvent.OnNavigateBack->{
                    navController.navigate(Screens.JoinCompanyUserPassword(companyID = companyID, email = email, hasPoppedBackStack = true))
                }
                is CreateUserProfileViewModel.CreateUserProfileUiEvent.OnNavigateToSignIn->{
                    navController.navigate(Screens.SignInScreen)
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
                    navigationIcon = {Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                           viewModel.onEvent(CreateUserProfileScreenEvent.OnNavigateUp)
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = stringResource(id = R.string.icon_descr),
                                tint = colorResource(id = R.color.text_color))
                        }
                        Text(text = stringResource(id = R.string.create_pass),
                            color = colorResource(id = R.color.text_color))
                    }},
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

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
                if(viewModel.user.value.profilePicture.isEmpty())
                {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.icon_descr),
                        modifier = Modifier.size(128.dp),
                        tint = Color.DarkGray,
                    )
                }
                else{
                    AsyncImage(model =viewModel.user.value.profilePicture,
                        contentDescription = stringResource(id = R.string.image_description),
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop)
                }
                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                ) {
                    Text(text = stringResource(id = R.string.select_profile_pic))
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                //first name
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.user.value.firstName,
                    onValueChange = {firstName->viewModel.onEvent(CreateUserProfileScreenEvent.OnFirstNameChange(firstName))},
                    placeholder = { Text(text = stringResource(id = R.string.first_name))},
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    supportingText = { Text(text = stringResource(id = R.string.Name_Supporting_Text))},
                    isError = viewModel.firstNameErr.value
                )
                //last name
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.user.value.lastName,
                    onValueChange = {lastName->viewModel.onEvent(CreateUserProfileScreenEvent.OnLastNameChange(lastName))},
                    placeholder = { Text(text = stringResource(id = R.string.last_name))},
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    supportingText = { Text(text = stringResource(id = R.string.Name_Supporting_Text))},
                    isError = viewModel.lastNameErr.value
                )
                //position
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.user.value.position,
                    onValueChange = {position->viewModel.onEvent(CreateUserProfileScreenEvent.OnPositionChange(position))},
                    placeholder = { Text(text = stringResource(id = R.string.position))},
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Work,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    supportingText = { Text(text = stringResource(id = R.string.job_title))},
                    isError = false
                )

                if (viewModel.lastNameErr.value||viewModel.firstNameErr.value || viewModel.registerErr.value) {
                    IconTextField(
                        text = viewModel.errMsg.value,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red,
                        iconSize = 24,
                        textSize = 16,
                        clickEn = false
                    ) {}
                }//if

                val btnEn=viewModel.user.value.firstName.isNotBlank()&& viewModel.user.value.lastName.isNotBlank() && viewModel.user.value.position.isNotBlank()
                Button(
                    onClick = {
                        viewModel.onEvent(CreateUserProfileScreenEvent.OnFinishBtnClick(companyID))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    enabled = btnEn,
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                ) {
                    Text(text = stringResource(id = R.string.finish_btn))
                }

            }
        }
    }
}
