package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.composables.CategoryHeader
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.composables.CategoryItem
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.composables.SearchArea

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SelectCountryScreen(
    viewModel: SelectCountryScreenViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is SelectCountryScreenViewModel.SelectCountryUiEvent.OnExitScreen->{
                    navController.navigate(Screens.CompanyDetailsScreen(event.country))
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
                SearchArea(
                    query = viewModel.searchedCountry.value,
                    onQueryChange = {searchedCountry->viewModel.onEvent(SelectCountryScreenEvent.OnQueryChange(searchedCountry))},
                    onCancelClick = {viewModel.onEvent(SelectCountryScreenEvent.OnCancelClick)}
                    )
                LazyColumn(
                    modifier = Modifier.weight(1f)
                )
                {
                    if (viewModel.searchedCountry.value.isEmpty())
                    {
                        viewModel.countriesList.forEach { category ->
                            stickyHeader {
                                CategoryHeader(text = category.name)
                            }
                            items(category.items){text->
                                CategoryItem(text = text, onClick = {viewModel.onEvent(SelectCountryScreenEvent.OnCountryClick(text))})
                                HorizontalDivider(thickness = 2.dp,
                                    color = colorResource(id = R.color.unfocused_color))
                            }
                        }
                    }
                    else{
                       items(viewModel.filteredList.value){text->
                           CategoryItem(text = text, onClick = {viewModel.onEvent(SelectCountryScreenEvent.OnCountryClick(text))})
                           HorizontalDivider(thickness = 2.dp,
                               color = colorResource(id = R.color.unfocused_color))
                       }
                    }

                }
            }
                }

        }
    }
