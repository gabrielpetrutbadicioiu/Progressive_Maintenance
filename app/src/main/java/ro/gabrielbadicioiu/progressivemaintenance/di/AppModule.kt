package ro.gabrielbadicioiu.progressivemaintenance.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.CreatePassUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.DoPasswordsMatch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.ShowPassResult
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.ShowPassword
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.ValidateCreatedPass
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.EmailValidationUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.ValidateEmail
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.SignInViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.OTPViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.EmailValidationViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_signUp.presentation.components.SignUpScreenViewModel

val appModule= module {


    //email validation screen
    single {
        EmailValidationUseCases(ValidateEmail())
    }

    single{
        CreatePassUseCases(
            validateCreatedPass = ValidateCreatedPass(),
            showPassword = ShowPassword(),
            doPasswordsMatch = DoPasswordsMatch()

        )
    }
    viewModel{
        CreatePassViewModel(get())
    }

    viewModel{
        OTPViewModel()
    }
viewModel {
    EmailValidationViewModel(get())
}

    viewModel{
            SignInViewModel()
             }
    viewModel{
        SignUpScreenViewModel()
    }
}