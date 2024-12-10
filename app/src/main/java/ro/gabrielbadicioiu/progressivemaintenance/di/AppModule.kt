package ro.gabrielbadicioiu.progressivemaintenance.di

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.data_source.UserDatabase
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository.AccountServiceImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository.UserRepositoryImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.UserRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.CreatePassUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.DoPasswordsMatch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.ShowPassword
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP.CountDown
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP.OTPUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP.OTPValidation
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OTP.OnResendOTPClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_create_pass.ValidateCreatedPass
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.FetchRememberedUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.RememberMe
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.SignIn
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_signIn.SignInUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName.SignUp
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName.NameValidation
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_userName.UserNameUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.EmailValidationUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_validate_email.ValidateEmail
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_create_pass.CreatePassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.SignInViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_OTP.OTPViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signUp_email_validation.EmailValidationViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_userName.UserNameViewModel


val appModule= module {

    single<AccountService>{
        AccountServiceImpl()
    }
    //email validation screen
    single {
        EmailValidationUseCases(ValidateEmail())
    }
//create pass screen
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
//OTP screen
    single{
        OTPUseCases(
            CountDown(),
            OnResendOTPClick(),
            OTPValidation()
        )
    }

    viewModel{
        OTPViewModel(get())
    }
    //username screen
    single{
        UserNameUseCases(
            NameValidation(),
            SignUp(get())
        )
    }
    viewModel {
        UserNameViewModel(get())
    }
viewModel {
    EmailValidationViewModel(get())
}
//sign in screen


    single {
        Room.databaseBuilder(get(),UserDatabase::class.java, "user-db").build().dao
    }
    single<UserRepository>
    {
        UserRepositoryImpl(get())
    }
    single{
        SignInUseCases(
            showPassword = ShowPassword(),
           signIn =  SignIn(
                accountService =get()

                ),
            rememberMe = RememberMe(get()),
            getRememberedUser = FetchRememberedUser(get())
        )
    }
    viewModel{
            SignInViewModel(get())
             }

}