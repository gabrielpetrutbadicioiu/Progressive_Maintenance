package ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Troubleshoot
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.BottomNavBar
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.components.ProductionLineCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
)
{
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            event->
            when(event)
            {
                is HomeViewModel.UiEvent.OnFabClick ->{
                    navController.navigate(Screens.AddEquipmentScreen)
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

                    title = { /*TODO*/
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

                    //search interventions
                    actions = {
                       IconButton(onClick = { /*TODO implementare ecran search ca pe tiktok cu recents cu d-alea*/ }) {
                           Icon(
                               imageVector = Icons.Default.Search,
                               contentDescription = stringResource(id = R.string.icon_descr),
                               tint = colorResource(id = R.color.text_color))
                       }
                        //add equipment
                        IconButton(onClick = {
                            viewModel.onEvent(HomeScreenEvent.OnAddProductionLineClick)
                        }) {
                            Icon(
                                imageVector = Icons.Default.LibraryAdd,
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
                BottomNavBar(selectedItemIndex = 0) {
                }
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
            }
        ) {
            innerPadding->
            val machines= listOf("Protos", "Focke", " Hauni")
            Column(modifier = Modifier.padding(innerPadding)) {
                ProductionLineCard(lineName = "mp49", onExpandClick = { /*TODO*/ }, isExpanded = true, lineMachines =machines )
            }
//            LazyColumn(modifier = Modifier.padding(innerPadding)) {
//                    items(100){
//                        Text(text = "item $it")
//                    }
//            }

        }//scaffold
    }//surface
}