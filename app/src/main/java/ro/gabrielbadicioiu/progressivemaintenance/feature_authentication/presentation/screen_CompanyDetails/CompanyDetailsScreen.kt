package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDetailsScreen(
     viewModel: CompanyDetailsViewModel,
     navController: NavController,
     selectedCountry:String
)
{
    LaunchedEffect(key1 = true) {
            viewModel.onEvent(CompanyDetailsScreenEvent.OnCountryInit(selectedCountry))
            viewModel.eventFlow.collectLatest { event->
                when(event){
                    is CompanyDetailsViewModel.CompanyDetailsUiEvent.OnCountrySelectClick->{
                        navController.navigate(Screens.SelectCountryScreen)
                    }
                    is CompanyDetailsViewModel.CompanyDetailsUiEvent.OnNavigateUp->{
                        navController.navigate(Screens.SignInScreen)
                    }
                    is CompanyDetailsViewModel.CompanyDetailsUiEvent.OnContinueClick->{
                        navController.navigate(Screens.CreateOwnerEmailScreen(
                            organisationName = viewModel.companyDetails.value.organisationName,
                            country = viewModel.companyDetails.value.country,
                            industry = viewModel.companyDetails.value.industryType))
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
                            Text(text = stringResource(id = R.string.SignIn_title),
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