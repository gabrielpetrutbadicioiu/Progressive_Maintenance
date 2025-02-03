package ro.gabrielbadicioiu.progressivemaintenance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.koin.androidx.viewmodel.ext.android.getViewModel
import ro.gabrielbadicioiu.progressivemaintenance.core.Screens
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_registerCompanyEmail.RegisterCompanyEmailScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.SignInScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.SignInViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.OTPScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.OTPViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.EmailValidationScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.EmailValidationViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName.UserNameScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName.UserNameViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.AddProductionLineScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.AddProductionLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.EditProdLineScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.EditProdLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.HomeScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.HomeViewModel


import ro.gabrielbadicioiu.progressivemaintenance.ui.theme.ProgressiveMaintenanceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProgressiveMaintenanceTheme(
                darkTheme = false
            ) {
               val navController= rememberNavController()
                val signInScreenViewModel=getViewModel<SignInViewModel>()
                val emailValidationViewModel=getViewModel<EmailValidationViewModel>()
                val otpViewModel=getViewModel<OTPViewModel>()
                val createPasswordViewmodel=getViewModel<CreatePassViewModel>()
                val userNameViewModel=getViewModel<UserNameViewModel>()
                val homeScreenViewModel=getViewModel<HomeViewModel>()
                val addProductionLineViewModel=getViewModel<AddProductionLineViewModel>()
                val editProdLineViewModel=getViewModel<EditProdLineViewModel>()
                val companyDetailsViewModel= getViewModel<CompanyDetailsViewModel>()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SignInScreen) {
                    composable<Screens.SignInScreen> {
                        SignInScreen(viewModel =signInScreenViewModel , navController = navController)
                    }
                    composable<Screens.EmailValidationScreen> {
                        EmailValidationScreen(viewModel = emailValidationViewModel, navController = navController)
                    }
                    composable<Screens.OTPScreen> {
                        val args= it.toRoute<Screens.OTPScreen>()
                        OTPScreen(viewModel = otpViewModel, navController = navController, args = args.email)
                    }
                    composable<Screens.CreatePassScreen> {
                        val args =it.toRoute<Screens.CreatePassScreen>()
                        CreatePassScreen(viewModel = createPasswordViewmodel, navController = navController, args =args.validatedEmail)
                    }
                    composable<Screens.UserNameScreen> {
                        val args=it.toRoute<Screens.UserNameScreen>()
                        UserNameScreen(
                            viewModel = userNameViewModel,
                            navController = navController,
                            validatedEmail = args.validatedEmail,
                            validatedPass = args.validatedPass
                        )
                    }
                    composable<Screens.AddEquipmentScreen> {
                        AddProductionLineScreen(viewModel = addProductionLineViewModel, navController = navController)
                    }
                    composable<Screens.HomeScreen> {
                        HomeScreen(viewModel = homeScreenViewModel, navController = navController)
                    }
                    composable<Screens.EditProdLineScreen> {
                        val args=it.toRoute<Screens.EditProdLineScreen>()
                        EditProdLineScreen(
                            viewModel = editProdLineViewModel,
                            navController = navController,
                            prodLineID = args.prodLineID)
                    }
                    composable<Screens.RegisterCompanyMailScreen> {
                        RegisterCompanyEmailScreen()
                    }
                    composable<Screens.CompanyDetailsScreen> {
                        CompanyDetailsScreen(viewModel = companyDetailsViewModel)
                    }
                }//navHost

            }
        }
    }
}

