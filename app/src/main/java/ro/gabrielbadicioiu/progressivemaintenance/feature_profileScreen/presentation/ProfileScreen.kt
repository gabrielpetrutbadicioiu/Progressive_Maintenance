package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import ro.gabrielbadicioiu.progressivemaintenance.R
import ro.gabrielbadicioiu.progressivemaintenance.core.ActiveScreen
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.BottomNavBar
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    companyId:String,
    userId:String,
    viewModel: ProfileScreenViewModel
)
{
    val context= LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(ProfileScreenEvent.OnGetArgumentData(companyId = companyId, userId = userId))
        viewModel.onEvent(ProfileScreenEvent.OnGetLoggedUser)

        viewModel.eventFlow.collectLatest { event->
            when(event)
            {
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateHome->{
                    navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateToMembers->{
                    navController.navigate(Screens.MembersScreenRoute(companyID = companyId, userId = userId))
                }
                is ProfileScreenViewModel.ProfileScreenUiEvent.OnNavigateToAddProductionLine->{
                    navController.navigate(Screens.AddProductionLineScreen(companyID = companyId, userID = userId))
                }
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val scrollBehavior= TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    navigationIcon = {
                        Row(horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically) {
                            if (!viewModel.fetchCompanyErr.value)
                            {
                                AsyncImage(model =viewModel.loggedCompany.value.companyLogoUrl,
                                    contentDescription = stringResource(id = R.string.image_description),
                                    modifier = Modifier
                                        .size(65.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop)
                                Column {
                                    Text(text = viewModel.loggedCompany.value.organisationName)
                                    Text(text = viewModel.loggedCompany.value.industryType)
                                }
                                
                            }
                        }

                    },
                    title = { //todo

                    },
                    //search interventions
                    actions = {
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
                    activeScreen = ActiveScreen.PROFILE,
                    userRank = viewModel.loggedUser.value.rank,
                    hasHomeBadge = false,
                    onHomeClick = { viewModel.onEvent(ProfileScreenEvent.OnNavigateHome)},
                    onActionBtnClick = {

                        if (viewModel.loggedUser.value.rank!= UserRank.USER.name)
                        {
                            viewModel.onEvent(ProfileScreenEvent.OnAddProductionLineClick)
                        }
                        else{
                            /*TODO*/                        }
                    },
                    onProfileScreenClick = { /*TODO*/ },
                    onMembersClick = { viewModel.onEvent(ProfileScreenEvent.OnNavigateToMembers)},
                    onStatisticsClick = { /*TODO*/  })


            },

        ) {
                innerPadding->

            LazyColumn(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {


            }//lazy column





        }//scaffold
    }//surface
}