package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerAccDetailsScreen(
    viewModel: OwnerAccDetailsViewModel,
    navController: NavController,
    companyDocumentID:String,
    userID:String?,
    userEmail:String?
)
{
val photoPickerLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
    uri->
    viewModel.onEvent(OwnerAccDetailsScreenEvent.OnUriResult(uri))
}

    LaunchedEffect(key1 = true) {

        viewModel.onEvent(OwnerAccDetailsScreenEvent.GetUserEmailAndID(
            currentUserEmail = userEmail.toString(),
            currentUserID = userID.toString()))
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is OwnerAccDetailsViewModel.OwnerAccDetailsUiEvent.OnNavigateUp->{
                    navController.navigateUp()
                }
                is OwnerAccDetailsViewModel.OwnerAccDetailsUiEvent.OnNavigateToSignInScreen->{
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
                    title = {},
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                viewModel.onEvent(OwnerAccDetailsScreenEvent.OnNavigateUp)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.company_details),
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


                if(viewModel.user.value.profilePicture.isEmpty())
                {
                    DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.profile_pic), size =128.dp )
                }
                else{
                    AsyncImage(model =viewModel.selectedImageUri.value,
                        contentDescription = stringResource(id = R.string.image_description),
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop)
                    }
                Spacer(modifier = Modifier.height(6.dp))
                Button(
                    onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.btn_color))
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
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(id = R.color.btn_color)),
                    onValueChange = {firstName->viewModel.onEvent(OwnerAccDetailsScreenEvent.OnFirstNameChange(firstName))},
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
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(id = R.color.btn_color)),
                    onValueChange = {lastName->viewModel.onEvent(OwnerAccDetailsScreenEvent.OnLastNameChange(lastName))},
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
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = colorResource(id = R.color.btn_color)),
                    onValueChange = {position->viewModel.onEvent(OwnerAccDetailsScreenEvent.OnPositionChange(position))},
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
                }

                    val btnEn=viewModel.user.value.firstName.isNotBlank()&& viewModel.user.value.lastName.isNotBlank() && viewModel.user.value.position.isNotBlank()
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            viewModel.onEvent(OwnerAccDetailsScreenEvent.OnFinishBtnClick(companyDocumentID))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        enabled = btnEn,
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.btn_color))
                    ) {
                        if (viewModel.showProgressBar.value)
                        {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp) ,
                                color = colorResource(id = R.color.bar_color)
                            )
                        }else{
                            Text(text = stringResource(id = R.string.finish_btn))
                        }

                    }




        }
    }
}}
