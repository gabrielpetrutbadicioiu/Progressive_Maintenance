package ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.InterventionParticipants
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.PmCardErrorState
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.model.ProgressiveMaintenanceCard
import ro.gabrielbadicioiu.progressivemaintenance.feature_logIntervention.domain.use_cases.LogInterventionScreenUseCases

class LogInterventionScreenViewModel(
    private val companiesRepository: CompaniesRepository,
    private val useCases: LogInterventionScreenUseCases
) :ViewModel()
{

    //states
    private val _author= mutableStateOf(UserDetails())
    val author: State<UserDetails> = _author

    private val _fetchAuthorError= mutableStateOf(false)
    val fetchAuthorError:State<Boolean> = _fetchAuthorError

    private val _fetchAuthorErrMsg= mutableStateOf("")
    val fetchAuthorErrMsg:State<String> = _fetchAuthorErrMsg

    private val _isShiftDropDownExpanded = mutableStateOf(false)
    val isShiftDropDownMenuExpanded:State<Boolean> = _isShiftDropDownExpanded

    private val _employeeList= mutableStateOf<List<UserDetails>>(emptyList())
    val employeeList:State<List<UserDetails>> = _employeeList

    private val _participantsList= mutableStateOf<List<UserDetails>>(emptyList())
    val participantsList:State<List<UserDetails>> = _participantsList

    private val _selectedParticipant= mutableStateOf(UserDetails())
   // val selectedParticipant:State<UserDetails> = _selectedParticipant

    private val _fetchEmployeesErr= mutableStateOf(false)
    val fetchEmployeesErr:State<Boolean> = _fetchEmployeesErr

    private val _fetchEmployeesErrMsg= mutableStateOf("")
    val fetchEmployeesErrMsg:State<String> = _fetchEmployeesErrMsg

    private val _isOtherParticipantsDropdownExpanded= mutableStateOf(false)
    val isOtherParticipantsDropdownMenuExpanded:State<Boolean> = _isOtherParticipantsDropdownExpanded

    private val _showStartDateDialog= mutableStateOf(false)
    val showStartDateDialog:State<Boolean> = _showStartDateDialog

    private val _showEndDateDialog= mutableStateOf(false)
    val showEndDateDialog:State<Boolean> = _showEndDateDialog

    private val _showStartTimeDialog= mutableStateOf(false)
    val showStartTimeDialog:State<Boolean> = _showStartTimeDialog

    private val _showEndTimeDialog= mutableStateOf(false)
    val showEndTimeDialog:State<Boolean> = _showEndTimeDialog

    private val _pmCard= mutableStateOf(ProgressiveMaintenanceCard())
    val pmCard:State<ProgressiveMaintenanceCard> = _pmCard

    private val _pmCardErrorState = mutableStateOf(PmCardErrorState())
    val pmCardErrorState:State<PmCardErrorState> = _pmCardErrorState

    //one time events
    sealed class LogInterventionUiEvent{
        data class ShowToast(val message:String):LogInterventionUiEvent()
        data object OnNavigateToHome:LogInterventionUiEvent()
    }
    private val _eventFlow= MutableSharedFlow<LogInterventionUiEvent>()
    val eventFlow= _eventFlow.asSharedFlow()




    @RequiresApi(Build.VERSION_CODES.O)
    fun onEvent(event:LogInterventionScreenEvent)
    {
        when(event)
        {
            is LogInterventionScreenEvent.GetArgumentData->{
                _pmCard.value=_pmCard.value.copy(authorId = event.userId, companyId = event.companyId)
                viewModelScope.launch {

                        try {
                            companiesRepository.getUserInCompany(
                                currentUserID = _pmCard.value.authorId,
                                companyID = _pmCard.value.companyId,
                                onSuccess = {user->
                                    if (user != null) {
                                        _author.value=user.copy()
                                        _pmCard.value=_pmCard.value.copy(authorAvatar = user.profilePicture)
                                        _fetchAuthorError.value=false
                                        _fetchAuthorErrMsg.value=""
                                    }
                                    else{
                                        _fetchAuthorError.value=true
                                        _fetchAuthorErrMsg.value="Viewmodel: User is null"
                                    }
                                },
                                onFailure = {e->
                                    _fetchAuthorError.value=true
                                    _fetchAuthorErrMsg.value=e
                                },
                                onUserNotFound = {
                                    _fetchAuthorError.value=true
                                    _fetchAuthorErrMsg.value="ViewModel: User not found"
                                }
                            )
                        }catch (e:Exception)
                        {
                            _fetchAuthorError.value=true
                            _fetchAuthorErrMsg.value=e.message?:"ViewModel: failed to fetch author"
                        }
                }
                //fetch all users
                viewModelScope.launch {
                    try {
                        companiesRepository.fetchAllUsersInCompany(
                            companyID = _pmCard.value.companyId,
                            onResult = {employeeList->
                                _employeeList.value=employeeList
                                _fetchEmployeesErr.value=false
                            },
                            onFailure = {e->
                                _fetchEmployeesErr.value=true
                                _fetchEmployeesErrMsg.value=e
                            }
                        )
                    }catch (e:Exception)
                    {
                        _fetchEmployeesErr.value=true
                        _fetchEmployeesErrMsg.value=e.message?:"Viewmodel: Failed to fetch employees"
                    }
                }
            }//fetch data

            is LogInterventionScreenEvent.OnShiftClick->{
                _pmCard.value=_pmCard.value.copy(shift = event.shift)
                 onEvent(LogInterventionScreenEvent.OnShiftDropdownDismiss)
            }
            is LogInterventionScreenEvent.OnExpandShiftDropDown->{
                _isShiftDropDownExpanded.value=true
            }
            is LogInterventionScreenEvent.OnShiftDropdownDismiss->{
                _isShiftDropDownExpanded.value=false
            }
            is LogInterventionScreenEvent.OnExpandOtherParticipantsDropdownMenu->{
                _isOtherParticipantsDropdownExpanded.value=true
            }
            is LogInterventionScreenEvent.OnOtherParticipantsDropdownMenuDismiss->{
                _isOtherParticipantsDropdownExpanded.value=false
            }
            is LogInterventionScreenEvent.OnParticipantClick->{
                _selectedParticipant.value=event.participant
                useCases.onSelectInterventionParticipant.execute(
                    selectedEmployee = _selectedParticipant.value,
                    participantList = _participantsList.value,
                    onFailure = {e->
                        viewModelScope.launch {_eventFlow.emit(LogInterventionUiEvent.ShowToast(e))}
                    },
                    onSuccess = {participants, interventionParticipants->
                        _participantsList.value=participants
                        _pmCard.value=_pmCard.value.copy(otherParticipants = interventionParticipants)
                        onEvent(LogInterventionScreenEvent.OnOtherParticipantsDropdownMenuDismiss)
                    }
                )
            }
            is LogInterventionScreenEvent.OnRemoveParticipant-> {
                _participantsList.value -= event.participant
                val deletedInterventionParticipant=InterventionParticipants(
                    firstName = event.participant.firstName,
                    lastName = event.participant.lastName,
                    avatar = event.participant.profilePicture,
                    id = event.participant.userID
                    )
                _pmCard.value=_pmCard.value.copy(otherParticipants = _pmCard.value.otherParticipants-deletedInterventionParticipant)
            }
            is LogInterventionScreenEvent.OnNavigateToHome->{
                viewModelScope.launch { _eventFlow.emit(LogInterventionUiEvent.OnNavigateToHome) }
            }

            is LogInterventionScreenEvent.OnSelectStartDateDismiss->{
                _showStartDateDialog.value=false
            }
            is LogInterventionScreenEvent.OnSelectStartDateClick->{
                _showStartDateDialog.value=true
            }
            is LogInterventionScreenEvent.OnGetStartDate->{
                _pmCard.value=_pmCard.value.copy(downtimeStartDate = event.startDate )
            }
            is LogInterventionScreenEvent.OnSelectEndDateClick->{
                _showEndDateDialog.value=true
            }
            is LogInterventionScreenEvent.OnEndDateDialogDismiss->{
                _showEndDateDialog.value=false
            }
            is LogInterventionScreenEvent.OnGetEndDate->{
                _pmCard.value=_pmCard.value.copy(downtimeEndDate = event.endDate)
            }
            is LogInterventionScreenEvent.OnDownTimeStartDialogClick->{
                _showStartTimeDialog.value=true
            }
            is LogInterventionScreenEvent.OnDownTimeStartDismiss->{
                _showStartTimeDialog.value=false
            }
            is LogInterventionScreenEvent.OnDownTimeEndDialogClick->{
                _showEndTimeDialog.value=true
            }
            is LogInterventionScreenEvent.OnDownTimeEndDismiss->{
                _showEndTimeDialog.value=false
            }
            is LogInterventionScreenEvent.OnGetDowntimeStartTime->{
                _pmCard.value=_pmCard.value.copy(downtimeStartTime = event.startTime )
                _showStartTimeDialog.value=false
            }
            is LogInterventionScreenEvent.OnGetDowntimeEndTime->{
                _pmCard.value=_pmCard.value.copy(downtimeEndTime = event.endTime)
                _showEndTimeDialog.value=false
            }
            is LogInterventionScreenEvent.OnGetTotalDowntimeDuration->{
                _pmCard.value=_pmCard.value.copy(totalDowntimeDuration = event.totalDowntimeDuration)
            }
            is LogInterventionScreenEvent.OnProblemDescriptionChange->{
                _pmCard.value=pmCard.value.copy(problemDescription = event.problemDescription.replaceFirstChar { char-> char.uppercase() })
            }
            is LogInterventionScreenEvent.OnProblemDetailingChange->{
                _pmCard.value=_pmCard.value.copy(problemDetailing = event.problemDetailing.replaceFirstChar { char-> char.uppercase() })
            }
            is LogInterventionScreenEvent.OnRootCauseChange->{
                _pmCard.value=_pmCard.value.copy(rootCause = event.rootCause.replaceFirstChar { char-> char.uppercase() })
            }
            is LogInterventionScreenEvent.OnObservationsChange->{
                _pmCard.value=_pmCard.value.copy(observations = event.obs.replaceFirstChar { char-> char.uppercase() })
            }
            is LogInterventionScreenEvent.OnTroubleshootStepsChange->{
                _pmCard.value=_pmCard.value.copy(troubleshootingSteps = event.steps.replaceFirstChar { char-> char.uppercase() })
            }
            is LogInterventionScreenEvent.OnLogInterventionClick->{
                try {
                    _pmCardErrorState.value=useCases.onLogInterventionClick.execute(
                        pmCard = _pmCard.value.copy()
                    )
                }catch (e:Exception)
                {
                    viewModelScope.launch {  _eventFlow.emit(LogInterventionUiEvent.ShowToast(e.message.toString()))}

                }

            }

        }
    }



}