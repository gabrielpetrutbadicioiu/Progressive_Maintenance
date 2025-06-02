package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

data class CreateProcedureUseCases(
    val onSaveProcedureClick: OnSaveProcedureClick,
    val onStepDescriptionChange: OnStepDescriptionChange,
    val onStepPhotoUriResult: OnStepPhotoUriResult,
    val procedurePhoto1Remove: ProcedurePhoto1Remove,
    val procedurePhoto2Remove: ProcedurePhoto2Remove,
    val procedurePhoto3Remove: ProcedurePhoto3Remove
)
