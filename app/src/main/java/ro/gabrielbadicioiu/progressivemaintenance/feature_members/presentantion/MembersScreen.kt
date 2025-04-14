package ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion

import android.widget.Toast
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import ro.gabrielbadicioiu.progressivemaintenance.core.ActiveScreen
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.BottomNavBar
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.DisplayLottie
import ro.gabrielbadicioiu.progressivemaintenance.core.composables.UserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.core.composables.IconTextField
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.composables.KickUserAlertDialog
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.composables.MemberCard


@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MembersScreen(
        companyId:String,
        userId:String,
        viewModel: MembersScreenViewModel,
        navController: NavController
    )
    {
        val context= LocalContext.current
        LaunchedEffect(key1 = true) {
            viewModel.onEvent(MembersScreenEvent.OnFetchArgumentData(companyId = companyId, userId = userId))
            viewModel.onEvent(MembersScreenEvent.OnFetchAllUsersInCompany)
            viewModel.eventFlow.collectLatest { event->
                when(event)
                {
                    is MembersScreenViewModel.MembersScreenUiEvent.OnAddProductionLineClick->{
                        navController.navigate(Screens.AddProductionLineScreen(companyID = companyId, userID = userId))
                    }
                    is MembersScreenViewModel.MembersScreenUiEvent.OnHomeBtnClick->{
                        navController.navigate(Screens.HomeScreen(companyID = companyId, userID = userId))
                    }
                    is MembersScreenViewModel.MembersScreenUiEvent.ToastMessage->{
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
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
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(stringResource(id = R.string.team_members))
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = colorResource(id = R.color.bar_color),
                            scrolledContainerColor = colorResource(id = R.color.scroll_bar_color)
                        )
                    )

                },
                bottomBar = {
                    BottomNavBar(
                        activeScreen = ActiveScreen.MEMBERS,
                        userRank = viewModel.userDetails.value.rank,
                        hasHomeBadge = false,//todo
                        onHomeClick = { viewModel.onEvent(MembersScreenEvent.OnHomeBtnClick) },
                        onActionBtnClick = {
                            if (viewModel.userDetails.value.rank!= UserRank.USER.name)
                            {
                                viewModel.onEvent(MembersScreenEvent.OnActionBtnClick)
                            }
                            else{
                                /*TODO*/  }
                        },
                        onProfileScreenClick = { /*TODO*/ },
                        onMembersClick = {},
                        onStatisticsClick = { /*TODO*/ })


                },
            )
            { innerPadding ->
                LazyColumn (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center)
                {
                    if (viewModel.isError.value)
                    {
                        item {
                            Column{
                                DisplayLottie(spec = LottieCompositionSpec.RawRes(R.raw.error), size = 128.dp)
                                IconTextField(
                                    text = viewModel.errMsg.value,
                                    icon = Icons.Default.WarningAmber,
                                    color = Color.Red,
                                    iconSize =24 ,
                                    textSize =20,
                                    clickEn =false
                                ) { }
                            }
                        }
                    }else
                    {
                        items(viewModel.memberStatus.value)
                        {user->
                            MemberCard(user = user.user,
                                currentUser = viewModel.userDetails.value,
                                isDropdownMenuExpanded = user.showDropDown,
                                onShowDropdownMenu = {
                                    viewModel.onEvent(MembersScreenEvent.OnShowDropdownMenu(user))
                                },
                                onDismissDropdownMenu = {viewModel.onEvent(MembersScreenEvent.OnDismissDropdownMenu)},
                                onChangeUserRank = {rank->
                                    viewModel.onEvent(MembersScreenEvent.OnChangeUserRank(user=user, rank = rank))
                                },
                                onEditPositionClick = {
                                    viewModel.onEvent(MembersScreenEvent.OnEditPositionClick(user))
                                },
                                onKickClick ={viewModel.onEvent(MembersScreenEvent.OnKickClick(user)) } )
                            Spacer(modifier = Modifier.height(16.dp))

                        }
                    }
                }
                EditPositionAlertDialog(
                    show =viewModel.showEditPositionAlertDialog.value ,
                    onDismissRequest = { viewModel.onEvent(MembersScreenEvent.OnDismissEditPositionAlertDialog) },
                    onConfirm = { viewModel.onEvent(MembersScreenEvent.OnUpdatePositionConfirm) },
                    onValueChange ={newPosition->
                        viewModel.onEvent(MembersScreenEvent.OnPositionValueChange(newPosition))
                    } ,
                    value = viewModel.tappedUser.value.user.position
                )
                KickUserAlertDialog(
                    tappedUser = viewModel.tappedUser.value,
                    onDismissRequest = { viewModel.onEvent(MembersScreenEvent.OnDismissKickDialog)},
                    onConfirm = { /*TODO*/ },
                    show = viewModel.showKickAlertDialog.value
                )
            }
        }
    }

