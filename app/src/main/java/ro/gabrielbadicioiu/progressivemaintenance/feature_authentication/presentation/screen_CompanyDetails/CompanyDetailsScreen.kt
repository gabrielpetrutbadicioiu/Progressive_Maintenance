package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
     viewModel: CompanyDetailsViewModel,
     navController: NavController,
     selectedCountry:String,
     currentUserID:String?,
     currentUserEmail:String?,
)
{
    val photoPickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
        uri->
        viewModel.onEvent(CompanyDetailsScreenEvent.OnUriResult(uri))
    }
    LaunchedEffect(key1 = true) {
            viewModel.onEvent(CompanyDetailsScreenEvent.GetUserEmailAndID
                (currentUserEmail = currentUserEmail.toString(),
                currentUserID = currentUserID.toString()))
            viewModel.onEvent(CompanyDetailsScreenEvent.OnCountryInit(selectedCountry))


            viewModel.eventFlow.collectLatest { event->
                when(event){
                    is CompanyDetailsViewModel.CompanyDetailsUiEvent.OnCountrySelectClick->{
                        navController.navigate(Screens.SelectCountryScreen
                            (currentUserId = viewModel.currentUserID.value,
                            currentUserEmail = viewModel.currentUserEmail.value))
                    }
                    is CompanyDetailsViewModel.CompanyDetailsUiEvent.OnNavigateUp->{
                        navController.navigate(Screens.CreateOwnerPassScreen(email = Firebase.auth.currentUser?.email.toString(), poppedBackStack = true))
                    }
                    is CompanyDetailsViewModel.CompanyDetailsUiEvent.OnContinueToOwnerDetails->{
                        navController.navigate(Screens.OwnerAccDetailsScreen(
                            companyDocumentID = event.documentID,
                            userEmail = viewModel.currentUserEmail.value,
                            userID = viewModel.currentUserID.value))
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
                    navigationIcon = 
                    {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.onEvent(CompanyDetailsScreenEvent.OnNavigateUp) }) {
                                Icon(
                                    imageVector =Icons.Default.ArrowBackIosNew ,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.create_pass),
                                color = colorResource(id = R.color.text_color))
                        }
                    },
                    title = {
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
                verticalArrangement = Arrangement.Center){

                if(viewModel.selectedImageUri.value==null)
                {
                  Image(
                      painter = painterResource(id = R.drawable.ic_unknown_company_logo),
                      contentDescription = stringResource(id = R.string.image_description),
                      modifier = Modifier
                          .size(128.dp)
                      )
                }
                else{
                    AsyncImage(
                        model = viewModel.selectedImageUri.value,
                        contentDescription = stringResource(id = R.string.image_description),
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop)
                }

                Button(
                    onClick = {
                       photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(16.dp, 0.dp),
                    colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                ) {
                    Text(text = stringResource(id = R.string.pick_company_logo))
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
                OutlinedTextField(
                    value = viewModel.companyDetails.value.organisationName,
                    onValueChange ={value->viewModel.onEvent(CompanyDetailsScreenEvent.OnOrganisationNameChange(value))},
                    supportingText = { Text(text = stringResource(id = R.string.organisation_name)) },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.enter_organisation_name)) },
                    singleLine = true,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
                OutlinedTextField(
                    value = viewModel.companyDetails.value.industryType,
                    onValueChange ={value->viewModel.onEvent(CompanyDetailsScreenEvent.OnIndustryNameChange(value))},
                    supportingText = { Text(text = stringResource(id = R.string.industry)) },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.industry_hint)) },
                    singleLine = true,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )

                OutlinedTextField(
                    value = viewModel.companyDetails.value.country,
                    readOnly = true,
                    onValueChange ={value->viewModel.onEvent(CompanyDetailsScreenEvent.OnCountryNameChange(value))},
                    supportingText = { Text(text = stringResource(id = R.string.country)) },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = { Text(text = stringResource(id = R.string.country_hint))},
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.onEvent(CompanyDetailsScreenEvent.OnSelectCountryClick) }) {
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
                        .padding(4.dp)
                )
                if (viewModel.isError.value)
                {
                    IconTextField(
                        text = viewModel.errorMessage.value,
                        icon = Icons.Default.WarningAmber,
                        color = Color.Red,
                        iconSize = 24,
                        textSize = 16,
                        clickEn =false
                    ) {}
                }
                if (viewModel.showProgressBar.value)
                {
                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp) ,
                        color = colorResource(id = R.color.bar_color)
                    )
                }
                else{
                    Button(
                        onClick = {viewModel.onEvent(CompanyDetailsScreenEvent.OnContinueClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp, 0.dp),
                        enabled = viewModel.companyDetails.value.organisationName.isNotEmpty()&&viewModel.companyDetails.value.industryType.isNotEmpty()&&viewModel.companyDetails.value.country.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.bar_color))
                    ) {
                        Text(text = stringResource(id = R.string.continue_btn))
                    }
                }

            }
        }
    }
}