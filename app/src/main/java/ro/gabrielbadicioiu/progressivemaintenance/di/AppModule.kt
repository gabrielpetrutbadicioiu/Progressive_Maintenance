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
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.EditProdLineUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.LoadProdLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnDeleteEditEquipment
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnDeleteEditProdLine
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnDoneEdit
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_editProdLine.OnEquipmentEdit
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.FetchProductionLines
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.GetUserInCompany
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.HideDropDownMenu
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.HomeScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.OnExpandBtnClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.OnProductionLineListener
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.domain.use_cases.screen_home.ShowDropDownMenu
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_addProductionLine.AddProductionLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_editProductionLine.EditProdLineViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_home.presentation.screen_home.HomeViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases.LogInterventionScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases.OnLogInterventionClick
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases.OnSelectInterventionParticipant
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation.LogInterventionScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases.FetchUsersInCompany
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases.MembersScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases.OnBanConfirm
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases.OnChangeUserRank
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.domain.use_cases.OnUpdateUserPosition
import ro.gabrielbadicioiu.progressivemaintenance.feature_members.presentantion.MembersScreenViewModel
import ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.domain.use_cases.ProfileScreenUseCases
import ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation.ProfileScreenViewModel


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
    //Profile screen
    single{
        ProfileScreenUseCases(
            getUserInCompany = GetUserInCompany(repository = get())
        )
    }
    viewModel {
        ProfileScreenViewModel(
            useCases = get(),
            companiesRepository = get(),
            cloudStorageRepository = get(),
            accountService = get()
            )
    }
// Members screen
    single{
        MembersScreenUseCases(
            getUserInCompany = GetUserInCompany(repository = get()),
            getAllUsersInCompany = FetchUsersInCompany(repository = get()),
            onChangeUserRank = OnChangeUserRank(repository = get()),
            onUpdateUserPosition = OnUpdateUserPosition(repository = get()),
            onBanConfirm = OnBanConfirm(repository = get())
        )
    }
    viewModel {
        MembersScreenViewModel(useCases = get())
    }
    //home screen
single{
    HomeScreenUseCases(
        fetchProductionLines = FetchProductionLines(get()),
        onExpandBtnClick = OnExpandBtnClick(),
        getUserById = GetUserInCompany(repository = get()),
        onProductionLineListener = OnProductionLineListener(repository = get()),
        onShowDropDownMenu = ShowDropDownMenu(),
        onHideDropDownMenu = HideDropDownMenu()
    )
}
    viewModel {
        HomeViewModel(get())
    }
    //add production line screen
    single{
        AddProductionLineScreenUseCases(
            onAddEquipmentClick = OnAddEquipmentClick(),
            onEquipmentDelete = OnEquipmentDelete(),
            onDoneBtnClick = OnDoneBtnClick(repository = get())
        )
    }
    viewModel {
        AddProductionLineViewModel(
            useCases = get()
        )
    }

//edit production line screen
    single{
        EditProdLineUseCases(
            loadProdLine = LoadProdLine(repository = get()),
            onEquipmentEdit = OnEquipmentEdit(),
            onDeleteEditEquipment = OnDeleteEditEquipment(),
            onDoneEdit = OnDoneEdit(repository = get()),
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

    //LogInterventionScreen
    single{
        LogInterventionScreenUseCases(
            onSelectInterventionParticipant = OnSelectInterventionParticipant(),
            onLogInterventionClick = OnLogInterventionClick()
        )
    }
    viewModel {
        LogInterventionScreenViewModel(
            companiesRepository = get(),
            useCases = get()
        )
    }
}

