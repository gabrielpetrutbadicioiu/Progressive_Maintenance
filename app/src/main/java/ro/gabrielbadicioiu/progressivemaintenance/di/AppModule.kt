package ro.gabrielbadicioiu.progressivemaintenance.di

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.data_source.UserDatabase
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository.AccountServiceImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository.CloudStorageRepositoryImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository.CompaniesRepositoryImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.data.repository.UserRepositoryImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.AccountService
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CloudStorageRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.UserRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_CreateOwnerEmail.CreateOwnerAccUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_CreateOwnerEmail.OnValidateEmailFormat
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.CheckUserInCompany
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.FetchRegisteredCompanies
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.GetCurrentFirebaseUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.GetRememberedUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.LoginScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.OnSignInClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.RememberUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails.IsValidName
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnAddUserToCompany
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_OwnerAccDetails.OwnerAccDetailsUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_SelectCountry.OnQueryChange
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_SelectCountry.SelectCountryUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_companyDetails.CompanyDetailsUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_companyDetails.OnRegisterCompany
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_companySelection.CompanySelectionUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnCompanySearch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core.OnRegisterUser
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_JoinCompanyUserPass.UserPassUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_createOwnerPass.OwnerPassUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_joinCompanyCreateUser.CreateUserUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_join_selectCompany.JoinSelectCompanyUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.SelectCountryScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_createOwnerEmail.CreateOwnerEmailViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CompanyDetails.CompanyDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_CreateOwnerPass.CreateOwnerPassViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserPassword.JoinCompanyUserPasswordScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_JoinCompanyUserProfile.CreateUserProfileViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_OwnerAccDetailsScreen.OwnerAccDetailsViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_companySelection.CompanySelectionViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_join_company_selectCompany.JoinSelectCompanyViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.screen_signIn.LoginViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.data.repository.ProductionLineRepositoryImpl
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.repository.ProductionLineRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.AddProductionLineScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.OnAddEquipmentClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.OnDoneBtnClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.OnEquipmentDelete
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.OnEquipmentNameChange
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_AddProductionLine.OnProductionLineNameChange
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.EditProdLineUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.LoadProdLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnDeleteEditEquipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnDeleteEditProdLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnDoneEdit
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnEquipmentEdit
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.FetchProductionLines
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.HomeScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.OnExpandBtnClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.AddProductionLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.EditProdLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.HomeViewModel


val appModule= module {
    single<ProductionLineRepository>{
        ProductionLineRepositoryImpl()
    }
    single<CloudStorageRepository>
    {
        CloudStorageRepositoryImpl()
    }
    single<CompaniesRepository>
    {
        CompaniesRepositoryImpl()
    }
    single<AccountService>{
        AccountServiceImpl()
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
        LoginScreenUseCases(
            rememberUser = RememberUser(get()),
            getRememberedUser = GetRememberedUser(get()),
            onSignIn = OnSignInClick(get()),
            sendVerificationEmail = ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login.SendVerificationEmail(get()),
            getCurrentFirebaseUser = GetCurrentFirebaseUser(),
            checkUserInCompany = CheckUserInCompany(get()),
            fetchRegisteredCompanies = FetchRegisteredCompanies(get()),
            onCompanySearch = OnCompanySearch()
        )
    }
    viewModel {
        LoginViewModel(get())
    }

    //home screen
single{
    HomeScreenUseCases(
        fetchProductionLines = FetchProductionLines(get()),
        onExpandBtnClick = OnExpandBtnClick()
    )
}
    viewModel {
        HomeViewModel(get())
    }
    //add production line screen
    single{
        AddProductionLineScreenUseCases(
            onProductionLineNameChange = OnProductionLineNameChange(),
            onAddEquipmentClick = OnAddEquipmentClick(),
            onEquipmentNameChange = OnEquipmentNameChange(),
            onEquipmentDelete = OnEquipmentDelete(),
            onDoneBtnClick = OnDoneBtnClick(get())
        )
    }
    viewModel {
        AddProductionLineViewModel(get())
    }

//edit production line screen
    single{
        EditProdLineUseCases(
            loadProdLine = LoadProdLine(get()),
            onEquipmentEdit = OnEquipmentEdit(),
            onDeleteEditEquipment = OnDeleteEditEquipment(),
            onDoneEdit = OnDoneEdit(get()),
            onDeleteEditProdLine = OnDeleteEditProdLine(get())
        )
    }
    viewModel {
        EditProdLineViewModel(get())
    }
    //company details screen
    single{
        CompanyDetailsUseCases(
            onRegisterCompany = OnRegisterCompany(
                companiesRepository = get(),
                cloudStorageRepository = get())
        )
    }
    viewModel {
        CompanyDetailsViewModel(
            get()
        )
    }
    //select country screen
    single{
        SelectCountryUseCases(
            onQueryChange = OnQueryChange()
        )
    }
    viewModel {
        SelectCountryScreenViewModel(get())
    }

    //Create owner email screen
    single{
        CreateOwnerAccUseCases(
            onValidateEmailFormat = OnValidateEmailFormat()
        )
    }
    viewModel {
        CreateOwnerEmailViewModel(get())
    }
    //create owner pass screen
    single{
        OwnerPassUseCases(
            onRegisterUser = OnRegisterUser(get())
        )
    }
    viewModel { CreateOwnerPassViewModel(get()) }
    //owner acc details screen
    single{
        OwnerAccDetailsUseCases(
            isValidName = IsValidName(),
            onAddUserToCompany = OnAddUserToCompany(
                companiesRepository = get(),
                cloudStorageRepository = get())
        )
    }
    viewModel { OwnerAccDetailsViewModel(get()) }
//company selection screen
single{
    CompanySelectionUseCases(
        onCompanySearch = OnCompanySearch()
    )
}
    viewModel { CompanySelectionViewModel(
        repository = get(),
        useCases = get()
        ) }
    //join select company screen
single{
    JoinSelectCompanyUseCases(
        fetchRegisteredCompanies = FetchRegisteredCompanies(get()),
        onCompanySearch = OnCompanySearch()
    )
}
    viewModel {
        JoinSelectCompanyViewModel(useCases = get())
    }
    //join select company user password screen
    single{
        UserPassUseCases(
            onRegisterUser = OnRegisterUser(get())
        )
    }
    viewModel {
        JoinCompanyUserPasswordScreenViewModel(useCases =get())
    }

    //join select company create user profile screen
    single{
        CreateUserUseCases(onAddUserToCompany = OnAddUserToCompany(companiesRepository = get(), cloudStorageRepository = get()))
    }
    viewModel {
        CreateUserProfileViewModel(get())
    }
}

