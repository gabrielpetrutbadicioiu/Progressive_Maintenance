package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases

data class CreateClUseCases(
    val addClParameter: AddClParameter,
    val clParameterNameChange: ClParameterNameChange,
    val clParameterValueChange: ClParameterValueChange,
    val deleteClParameter: DeleteClParameter,
    val onSaveClick: OnSaveClick
)
