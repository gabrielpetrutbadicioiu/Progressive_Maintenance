package ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.Divider
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.SearchField
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards.composables.InterventionCard
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards.composables.SortArea

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayAllPMCardsScreen(
    viewModel: DisplayAllPmCardsViewModel,
    navController: NavController,
    displayAllInterventions:Boolean,
    displayLineInterventions:Boolean,
    displayEquipmentInterventions:Boolean,
    companyId:String,
    userId:String,
    lineId:String,
    equipmentId:String,

)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true) {

        viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnGetArgumentData(
            displayAllInterventions=displayAllInterventions,
            displayLineInterventions=displayLineInterventions,
            displayEquipmentInterventions=displayEquipmentInterventions,
            companyId=companyId,
            userId=userId,
            lineId=lineId,
            equipmentId=equipmentId,
        ))
        viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnGetAllPmCards)
        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is DisplayAllPmCardsViewModel.DisplayAllPmCardsUiEvent.OnNavigateHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
                }
                is DisplayAllPmCardsViewModel.DisplayAllPmCardsUiEvent.OnShowToast->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is DisplayAllPmCardsViewModel.DisplayAllPmCardsUiEvent.OnNavigateToViewIntervention->{
                    navController.navigate(Screens.LogInterventionScreen(
                        companyId = companyId,
                        userId = userId,

                        productionLineId = event.productionLineId,
                        equipmentId = event.equipmentId,
                        interventionId = event.interventionId,
                        readOnly = true,
                        prodLineName = "",//will be taken from cloud
                        equipmentName = ""
                    ))
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
                        IconButton(onClick = {viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnNavigateHome)}) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowBackIosNew,
                                contentDescription = stringResource(id = R.string.icon_descr) )
                        }
                    },
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                          Text(text = stringResource(id = R.string.search_intervention))


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
                verticalArrangement = Arrangement.Top)
            {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                  SearchField(
                      query = viewModel.query.value,
                      onQueryChange ={query-> viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnQueryChange(query = query.replaceFirstChar { char-> char.uppercase() }))},
                      )
                    Divider(space = 8.dp, thickness = 2.dp, color = Color.LightGray)
                    if (viewModel.isErr.value)
                    {
                        IconTextField(
                            text = viewModel.fetchInterventionsErr.value,
                            icon = Icons.Default.Warning,
                            color = Color.Red,
                            iconSize = 24,
                            textSize = 24,
                            clickEn = false,
                            onClick = {}
                        ) 
                    }
                }
                item { SortArea(
                    sortOption = viewModel.sortOption.value,
                    onSortByDuration = {viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnSortInterventionsByDuration)},
                    onSortByDate = {viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnSortInterventionsByDate)},
                    onSortUnresolvedFirst = {viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnSortUnresolvedFirst)},
                    onSortResolvedFirst={viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnSortResolvedFirst)}
                )
                    Divider(space = 8.dp, thickness = 2.dp, color = Color.LightGray)
                }
if (viewModel.pmCardList.value.isEmpty())
{
    item { Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, 
        verticalArrangement = Arrangement.Center) {
        DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.ghost_lottie), size = 128.dp)
        Text(text = stringResource(id = R.string.no_intervention_found))
    } }
}
                
                itemsIndexed(viewModel.pmCardList.value) {index, pmCard->
                    InterventionCard(
                        pmCard = pmCard,
                        isExpanded = viewModel.pmCardList.value[index].isExpanded,
                        onExpandClick = {viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnExpandInterventionClick(index))},
                        onViewDetailsClick = {viewModel.onEvent(DisplayAllPmCardsScreenEvent.OnViewInterventionDetailsClick(pmCard))})
                }

            }
        }
    }
}
