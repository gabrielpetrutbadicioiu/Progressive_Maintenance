package ro.gabrielbadicioiu.progressivemaintenance.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ro.gabrielbadicioiu.progressivemaintenance.feature_signIn.presentation.LoginViewModel

val appModule= module {

    viewModel{
LoginViewModel()
}
}