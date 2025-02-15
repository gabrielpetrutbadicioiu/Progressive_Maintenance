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
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.SelectCountryScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.SelectCountryScreenViewModel
//import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.CreateOwnerAccountScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.CreateOwnerEmailScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.CreateOwnerEmailViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass.CreateOwnerPassScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass.CreateOwnerPassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen.OwnerAccDetailsScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen.OwnerAccDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_registerCompanyEmail.RegisterCompanyEmailScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.LogInScreen
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.LoginViewModel
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
                val loginViewModel=getViewModel<LoginViewModel> ()
                val emailValidationViewModel=getViewModel<EmailValidationViewModel>()
                val otpViewModel=getViewModel<OTPViewModel>()
                val createPasswordViewmodel=getViewModel<CreatePassViewModel>()
                val userNameViewModel=getViewModel<UserNameViewModel>()
                val homeScreenViewModel=getViewModel<HomeViewModel>()
                val addProductionLineViewModel=getViewModel<AddProductionLineViewModel>()
                val editProdLineViewModel=getViewModel<EditProdLineViewModel>()
                val companyDetailsViewModel= getViewModel<CompanyDetailsViewModel>()
                val selectCountryScreenViewModel=getViewModel<SelectCountryScreenViewModel> ()
                val createOwnerEmailViewModel=getViewModel<CreateOwnerEmailViewModel>()
                val createOwnerPassViewModel= getViewModel<CreateOwnerPassViewModel> ()
                val ownerAccDetailsViewModel=getViewModel<OwnerAccDetailsViewModel> ()
                NavHost(
                    navController = navController,
                    startDestination = Screens.SignInScreen) {
                    composable<Screens.SignInScreen> {
                        LogInScreen(viewModel = loginViewModel,
                                    navController = navController)
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
                        val args=it.toRoute<Screens.CompanyDetailsScreen>()
                        CompanyDetailsScreen(
                            viewModel = companyDetailsViewModel,
                            navController = navController,
                            selectedCountry = args.selectedCountry
                            )
                    }
                    composable<Screens.SelectCountryScreen> {
                        SelectCountryScreen(
                            viewModel = selectCountryScreenViewModel,
                            navController = navController
                            )
                    }
                    composable<Screens.CreateOwnerEmailScreen> {
                        val args=it.toRoute<Screens.CreateOwnerEmailScreen>()
                        val companyDetails=Company(
                            organisationName = args.organisationName,
                            country = args.country,
                            industryType = args.industry
                        )
                        CreateOwnerEmailScreen(companyDetails =
                        companyDetails,
                            viewModel =createOwnerEmailViewModel ,
                            navController = navController)

                    }
                    composable<Screens.CreateOwnerPassScreen> {
                        val args=it.toRoute<Screens.CreateOwnerPassScreen>()
                        val companyDetails=Company(organisationName = args.organisationName, industryType = args.industry, country = args.country)
                        CreateOwnerPassScreen(
                            email = args.email,
                            viewModel = createOwnerPassViewModel,
                            company = companyDetails,
                            navController = navController
                        )
                    }
                    composable<Screens.OwnerAccDetailsScreen> {
                        val args= it.toRoute<Screens.OwnerAccDetailsScreen>()
                        val company=Company(country = args.country, industryType = args.industry, organisationName = args.organisationName)
                        OwnerAccDetailsScreen(email = args.email,
                            pass = args.password,
                            company = company,
                            navController = navController,
                            viewModel = ownerAccDetailsViewModel
                            )
                    }
                }//navHost

            }
        }
    }
}

