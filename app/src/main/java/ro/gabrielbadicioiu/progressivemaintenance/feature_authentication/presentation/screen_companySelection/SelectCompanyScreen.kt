package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.components.CompanySearchArea

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SelectCompanyScreen(
viewModel: CompanySelectionViewModel,
navController: NavController
)
{
    LaunchedEffect(key1 = true) {
    viewModel.onEvent(CompanySelectionScreenEvent.FetchCompanies)
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is CompanySelectionViewModel.CompanySelectionUiEvent.OnNavigateUp->{
                    navController.navigateUp()
                }
                is CompanySelectionViewModel.CompanySelectionUiEvent.OnNavigateToLogin->{
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
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {
                CompanySearchArea(
                    query = viewModel.searchedValue.value,
                    onQueryChange = {value->viewModel.onEvent(CompanySelectionScreenEvent.OnSearchedCompany(value))},
                    onCancelClick = {viewModel.onEvent(CompanySelectionScreenEvent.OnCancelBtnClick)}
                )
                LazyColumn(
                    modifier = Modifier.weight(1f)
                )
                {
                val companies=if (viewModel.searchedValue.value.isEmpty()) viewModel.companies.value else {viewModel.filteredCompanies.value}
                    if (companies.isEmpty())
                    {
                        item { 
                            Text(text = stringResource(id = R.string.company_searched_fail),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center)
                        }
                    }
                    else{
                        items(companies)
                        {company->



                        }
                    }


                }
            }
        }

    }
}
