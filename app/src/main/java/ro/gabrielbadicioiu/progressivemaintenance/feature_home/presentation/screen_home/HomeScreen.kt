package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Troubleshoot
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.BottomNavBar
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components.EmptyScreenCard
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components.ProductionLineCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    companyId:String,
    userId:String
)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(HomeScreenEvent.OnFetchProductionLines)
        viewModel.eventFlow.collectLatest {
            event->
            when(event)
            {
                is HomeViewModel.HomeScreenUiEvent.OnFabClick ->{
                    navController.navigate(Screens.AddEquipmentScreen)
                }
                is HomeViewModel.HomeScreenUiEvent.ToastMessage->{
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is HomeViewModel.HomeScreenUiEvent.OnEditBtnClick->{
                    navController.navigate(Screens.EditProdLineScreen(prodLineID = event.id))
                }
                is HomeViewModel.HomeScreenUiEvent.OnNavigateTo->{
                    navController.navigate(event.screen)
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val scrollBehavior=TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { viewModel.onEvent(HomeScreenEvent.OnNavigateUp)}) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = stringResource(id = R.string.icon_descr),
                                    tint = colorResource(id = R.color.text_color))
                            }
                            Text(text = stringResource(id = R.string.SignIn_title),
                                color = colorResource(id = R.color.text_color))
                        }
                    },
                    title = { /*TODO*/
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                        }
                    },
                    //search interventions
                    actions = {
                       IconButton(onClick = { /*TODO implementare ecran search ca pe tiktok cu recents cu d-alea*/ }) {
                           Icon(
                               imageVector = Icons.Default.Search,
                               contentDescription = stringResource(id = R.string.icon_descr),
                               tint = colorResource(id = R.color.text_color))
                       }
                        
                        //notification bell
                        IconButton(onClick = { /*TODO*/ }) {

                            Icon(
                                imageVector = Icons.Default.NotificationsNone,
                                contentDescription = stringResource(id = R.string.warning_icon_descr),
                                tint = colorResource(id = R.color.text_color)
                            )
                        }
                    },//actions
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.bar_color),
                        scrolledContainerColor = colorResource(id = R.color.scroll_bar_color)
                    ),
                    )//topAppBar
            },//topBar
            bottomBar = {
                BottomNavBar(
                    selectedItemIndex = 0,
                    onClick = {},
                    onAddEquipmentClick ={viewModel.onEvent(HomeScreenEvent.OnAddProductionLineClick)} )
            },
            floatingActionButton = {
                FloatingActionButton(
                    modifier = Modifier.padding(4.dp),
                    shape = CircleShape,
                    containerColor = colorResource(id = R.color.bar_color),
                    contentColor = colorResource(id = R.color.text_color),
                    onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Troubleshoot, contentDescription = stringResource(id = R.string.warning_icon_descr))
                }
            },
        ) {
            innerPadding->

            LazyColumn(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                item {   Text(text = companyId)
                    Text(text = userId) }
                if (viewModel.showProgressBar.value)
                {
                    item {

                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp) ,
                            color = colorResource(id = R.color.bar_color),

                        )
                    }
                }
                else{
                    if (viewModel.fetchProdLineErr.value)
                    {
                        item {
                            Text(text = viewModel.failedToFetchProdLinesErrMsg.value)
                        }

                    }
                    else{
                        if(viewModel.productionLineList.value.isEmpty())
                        {
                            item {EmptyScreenCard()  }
                        }
                        else{
                            items(viewModel.productionLineList.value.size)
                            {index->
                                ProductionLineCard(
                                    productionLine = viewModel.productionLineList.value[index],
                                    onExpandClick = {viewModel.onEvent(HomeScreenEvent.OnExpandBtnClick(viewModel.productionLineList.value[index].id)) },
                                    isExpanded = viewModel.productionLineList.value[index].isExpanded,
                                    onEditClick = {viewModel.onEvent(HomeScreenEvent.OnEditBtnClick(viewModel.productionLineList.value[index].id))}
                                )
                            }

                        }
                    }

                }



            }





        }//scaffold
    }//surface
}