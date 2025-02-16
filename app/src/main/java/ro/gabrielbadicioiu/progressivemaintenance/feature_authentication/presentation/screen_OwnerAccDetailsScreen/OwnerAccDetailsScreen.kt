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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Person
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
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerAccDetailsScreen(
    viewModel: OwnerAccDetailsViewModel,
    navController: NavController,
    email:String,
    pass:String,
    company: Company
)
{
val photoPickerLauncher= rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
    uri->
    viewModel.onEvent(OwnerAccDetailsScreenEvent.OnUriResult(uri))
}
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is OwnerAccDetailsViewModel.OwnerAccDetailsUiEvent.OnNavigateUp->{
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
                            Text(text = stringResource(id = R.string.create_pass),
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

                if(viewModel.selectedImageUri.value==null)
                {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.icon_descr),
                        modifier = Modifier.size(128.dp),
                        tint = Color.DarkGray,

                    )
                }
                else{
                    AsyncImage(model =viewModel.selectedImageUri.value ,
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
                    value = viewModel.firstName.value,
                    onValueChange = {firstName->viewModel.onEvent(OwnerAccDetailsScreenEvent.OnFirstNameChange(firstName))},
                    placeholder = { Text(text = stringResource(id = R.string.first_name))},
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    supportingText = { Text(text = viewModel.firstNameSupportingText.value)},
                    isError = false//todo
                )
                //last name
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.lastName.value,
                    onValueChange = {lastName->viewModel.onEvent(OwnerAccDetailsScreenEvent.OnLastNameChange(lastName))},
                    placeholder = { Text(text = stringResource(id = R.string.last_name))},
                    singleLine = true,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.icon_descr))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    supportingText = { Text(text = viewModel.lastNameSupportingText.value)},
                    isError = false//todo
                )

                //position
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = viewModel.position.value,
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



                Button(
                    onClick = {
                        /*TODO*/
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 0.dp),
                    enabled = false,//TODO
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                ) {
                    Text(text = stringResource(id = R.string.finish_btn))
                }

            }
        }
    }
}
