package ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_profileScreen.domain.use_cases.ProfileScreenUseCases

class ProfileScreenViewModel(
    private val useCases: ProfileScreenUseCases,
    private val companiesRepository: CompaniesRepository
):ViewModel()
{
    //states
    private val _userId= mutableStateOf("")
    val userId:State<String> =_userId

    private val _companyId= mutableStateOf("")
    val companyId:State<String> = _companyId

    private val _loggedUser= mutableStateOf(UserDetails())
    val loggedUser:State<UserDetails> =_loggedUser

    private val _fetchUserErr= mutableStateOf(false)
    val fetchUserErr:State<Boolean> = _fetchUserErr

    private val _fetchUserErrMsg= mutableStateOf("")
    val fetchUserErrMsg:State<String> = _fetchUserErrMsg

    private val _fetchCompanyErr= mutableStateOf(false)
    val fetchCompanyErr:State<Boolean> = _fetchCompanyErr

    private val _fetchCompanyErrMsg= mutableStateOf("")
    val fetchCompanyErrMsg:State<String> = _fetchCompanyErrMsg

    private val _loggedCompany= mutableStateOf(Company())
    val loggedCompany:State<Company> = _loggedCompany

    //one time events
    private val _eventFlow= MutableSharedFlow<ProfileScreenUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()

    sealed class ProfileScreenUiEvent{
        data object OnNavigateHome:ProfileScreenUiEvent()
        data object OnNavigateToMembers:ProfileScreenUiEvent()
        data object OnNavigateToAddProductionLine:ProfileScreenUiEvent()
    }

    fun onEvent(event: ProfileScreenEvent)
    {
        when(event)
        {
            is ProfileScreenEvent.OnNavigateHome->{
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateHome) }
            }
            is ProfileScreenEvent.OnNavigateToMembers->{
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateToMembers) }
            }
            is ProfileScreenEvent.OnGetLoggedUser->{
                try {
                    viewModelScope.launch {
                        companiesRepository.getCompanyById(
                            companyId = _companyId.value,
                            onSuccess = {company ->
                                _fetchCompanyErr.value=false
                                _fetchCompanyErrMsg.value=""
                                _loggedCompany.value=company.copy()
                            },
                            onFailure = {e->
                                _fetchCompanyErr.value=true
                                _fetchCompanyErrMsg.value=e
                            }
                        )
                    }
                }
                catch (e:Exception)
                {
                    _fetchCompanyErrMsg.value=e.message?:"Viewmodel:Failed to fetch company"
                }

                try {
                    viewModelScope.launch {
                        useCases.getUserInCompany.execute(
                            userId = _userId.value,
                            companyId = _companyId.value,
                            onSuccess = {currentUser->
                                _loggedUser.value=currentUser
                                _fetchUserErr.value=false
                                _loggedUser.value=currentUser.copy()
                            },
                            onFailure = {e->
                                _fetchUserErr.value=true
                                _fetchUserErrMsg.value=e
                            }
                        )
                    }
                }catch (e:Exception){
                    _fetchUserErr.value=true
                    _fetchUserErrMsg.value=e.message?:"Failed to fetch user!"
                }

            }
            is ProfileScreenEvent.OnGetArgumentData->{
                _userId.value=event.userId
                _companyId.value=event.companyId
            }
            is ProfileScreenEvent.OnAddProductionLineClick->{
                viewModelScope.launch { _eventFlow.emit(ProfileScreenUiEvent.OnNavigateToAddProductionLine) }
            }
        }
    }
}