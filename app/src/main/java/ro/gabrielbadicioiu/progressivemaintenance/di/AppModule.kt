package ro.gabrielbadicioiu.progressivemaintenance.di

import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.LoginViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.components.SignUpScreenViewModel

val appModule= module {


    viewModel{
            LoginViewModel()
             }
    viewModel{
        SignUpScreenViewModel()
    }
}