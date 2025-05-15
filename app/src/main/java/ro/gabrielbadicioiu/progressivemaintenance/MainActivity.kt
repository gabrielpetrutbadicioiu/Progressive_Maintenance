package ro.gabrielbadicioiu.progressivemaintenance

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

import org.koin.androidx.viewmodel.ext.android.getViewModel
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.SelectCountryScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.SelectCountryScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.CreateOwnerEmailScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.CreateOwnerEmailViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass.CreateOwnerPassScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass.CreateOwnerPassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserPassword.JoinCompanyUserPasswordScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserPassword.JoinCompanyUserPasswordScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserProfile.CreateUserProfileViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserProfile.JoinCompanyUserProfileScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen.OwnerAccDetailsScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen.OwnerAccDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection.CompanySelectionViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection.SelectCompanyScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_join_company_selectCompany.JoinSelectCompanyScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_join_company_selectCompany.JoinSelectCompanyViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_registerCompanyEmail.RegisterCompanyEmailScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.LogInScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.LoginViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards.DisplayAllPMCardsScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_displayInterventions.presentation.screen_displayAllIPMCards.DisplayAllPmCardsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.AddProductionLineScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.AddProductionLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_bannedScreen.BannedScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.EditProdLineScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.EditProdLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.HomeScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.HomeViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.LogInterventionScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.LogInterventionScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.MembersScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.MembersScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation.ProfileScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation.ProfileScreenViewModel


import ro.gabrielbadicioiu.progressivemaintenance.ui.theme.ProgressiveMaintenanceTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressiveMaintenanceTheme(
                darkTheme = true
            ) {
               val navController= rememberNavController()

                val loginViewModel=getViewModel<LoginViewModel> ()
                val homeScreenViewModel=getViewModel<HomeViewModel>()
                val addProductionLineViewModel=getViewModel<AddProductionLineViewModel>()
                val editProdLineViewModel=getViewModel<EditProdLineViewModel>()
                val companyDetailsViewModel= getViewModel<CompanyDetailsViewModel>()
                val selectCountryScreenViewModel=getViewModel<SelectCountryScreenViewModel> ()
                val createOwnerEmailViewModel=getViewModel<CreateOwnerEmailViewModel>()
                val createOwnerPassViewModel= getViewModel<CreateOwnerPassViewModel> ()
                val ownerAccDetailsViewModel=getViewModel<OwnerAccDetailsViewModel> ()
                val companySelectionViewModel=getViewModel<CompanySelectionViewModel> ()
                val joinSelectCompanyViewModel=getViewModel<JoinSelectCompanyViewModel>()
                val joinCompanyUserPasswordScreenViewModel=getViewModel<JoinCompanyUserPasswordScreenViewModel> ()
                val createUserProfileViewModel=getViewModel<CreateUserProfileViewModel>()
                val membersScreenViewModel=getViewModel<MembersScreenViewModel>()
                val profileScreenViewModel=getViewModel<ProfileScreenViewModel> ()
                val logInterventionScreenViewModel= getViewModel<LogInterventionScreenViewModel> ()
                val displayAllPmCardsViewModel= getViewModel<DisplayAllPmCardsViewModel> ()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SignInScreen) {
                    composable<Screens.SignInScreen> {

                        LogInScreen(
                            viewModel = loginViewModel,
                                    navController = navController,
                           )
                    }

                    composable<Screens.AddProductionLineScreen> {
                        val args=it.toRoute<Screens.AddProductionLineScreen>()
                        AddProductionLineScreen(
                            viewModel = addProductionLineViewModel,
                            navController = navController,
                            companyID = args.companyID,
                            userID = args.userID)
                    }
                    composable<Screens.HomeScreen> {
                        val args=it.toRoute<Screens.HomeScreen>()
                        HomeScreen(viewModel = homeScreenViewModel,
                            navController = navController,
                            userId =args.userID,
                            companyId = args.companyID)

                    }
                    composable<Screens.EditProdLineScreen> {
                        val args=it.toRoute<Screens.EditProdLineScreen>()
                        EditProdLineScreen(
                            viewModel = editProdLineViewModel,
                            navController = navController,
                            prodLineId = args.productionLineId,
                            userId = args.userID,
                            companyId = args.companyId)
                    }
                    composable<Screens.RegisterCompanyMailScreen> {
                        RegisterCompanyEmailScreen()
                    }
                    composable<Screens.CompanyDetailsScreen> {
                        val args=it.toRoute<Screens.CompanyDetailsScreen>()
                        CompanyDetailsScreen(
                            viewModel = companyDetailsViewModel,
                            navController = navController,
                            selectedCountry = args.selectedCountry,
                            currentUserEmail = args.userEmail,
                            currentUserID = args.userID,
                            )
                    }
                    composable<Screens.SelectCountryScreen> {
                        val args=it.toRoute<Screens.SelectCountryScreen>()
                        SelectCountryScreen(
                            viewModel = selectCountryScreenViewModel,
                            navController = navController,
                            currentUserEmail = args.currentUserEmail,
                            currentUserId = args.currentUserId
                            )
                    }
                    composable<Screens.CreateOwnerEmailScreen> {
                        CreateOwnerEmailScreen(
                            viewModel =createOwnerEmailViewModel,
                            navController = navController)

                    }
                    composable<Screens.CreateOwnerPassScreen> {
                        val args=it.toRoute<Screens.CreateOwnerPassScreen>()
                        CreateOwnerPassScreen(
                            email = args.email,
                            poppedBackStack = args.poppedBackStack,
                            viewModel = createOwnerPassViewModel,
                            navController = navController
                        )
                    }
                    composable<Screens.OwnerAccDetailsScreen> {
                        val args= it.toRoute<Screens.OwnerAccDetailsScreen>()

                        OwnerAccDetailsScreen(
                            companyDocumentID = args.companyDocumentID,
                            navController = navController,
                            viewModel = ownerAccDetailsViewModel,
                            userID = args.userID,
                            userEmail = args.userEmail
                            )
                    }
                    composable<Screens.SelectCompanyScreen> {
                        SelectCompanyScreen(
                            viewModel = companySelectionViewModel,
                            navController=navController
                        )
                    }
                    composable<Screens.JoinSelectCompanyScreen> {
                        JoinSelectCompanyScreen(
                            viewModel = joinSelectCompanyViewModel,
                            navController = navController
                        )
                    }
                    composable<Screens.JoinCompanyUserPassword> {
                        val args=it.toRoute<Screens.JoinCompanyUserPassword>()
                        JoinCompanyUserPasswordScreen(
                            email =args.email ,
                            companyId = args.companyID,
                            navController = navController,
                            viewModel = joinCompanyUserPasswordScreenViewModel,
                            poppedBackStack = args.hasPoppedBackStack)
                    }

                    composable<Screens.JoinCompanyCreateUserProfile> {
                        val args=it.toRoute<Screens.JoinCompanyCreateUserProfile>()
                        JoinCompanyUserProfileScreen(
                            viewModel = createUserProfileViewModel,
                            navController = navController,
                            companyID =args.companyID ,
                            userID = args.userID,
                            email = args.email
                        )
                    }
                    composable<Screens.MembersScreenRoute> {
                        val args=it.toRoute<Screens.MembersScreenRoute>()
                        MembersScreen(
                            companyId = args.companyID,
                            userId =args.userId ,
                            viewModel = membersScreenViewModel,
                            navController = navController)
                    }
                    composable<Screens.BannedScreen> {
                        BannedScreen()
                    }
                    composable<Screens.ProfileScreenRoute> {
                        val args=it.toRoute<Screens.ProfileScreenRoute>()
                        ProfileScreen(
                            navController =navController ,
                            companyId =args.companyId ,
                            userId = args.userId,
                            viewModel =profileScreenViewModel )
                    }
                    composable<Screens.LogInterventionScreen> {
                        val args=it.toRoute<Screens.LogInterventionScreen>()
                        LogInterventionScreen(
                            companyId =args.companyId ,
                            userId =args.userId,
                            productionLineId = args.productionLineId,
                            equipmentId = args.equipmentId,
                            equipmentName = args.equipmentName,
                            prodLineName = args.prodLineName,
                            navController = navController,
                            viewModel = logInterventionScreenViewModel
                            )
                    }
                    composable<Screens.DisplayInterventionsScreen> {
                        val args=it.toRoute<Screens.DisplayInterventionsScreen>()
                        DisplayAllPMCardsScreen(
                            viewModel = displayAllPmCardsViewModel,
                            navController = navController,
                            displayAllInterventions = args.displayAllInterventions,
                            displayLineInterventions = args.displayLineInterventions,
                            displayEquipmentInterventions = args.displayEquipmentInterventions,
                            companyId = args.companyId,
                            userId = args.userId,
                            lineId = args.lineId,
                            equipmentId =args.equipmentId
                        )
                    }
                }//navHost

            }
        }
    }
}

