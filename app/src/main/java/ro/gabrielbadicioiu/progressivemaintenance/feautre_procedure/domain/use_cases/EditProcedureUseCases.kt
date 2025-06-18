package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

data class EditProcedureUseCases(
    val procedurePhoto1Remove: ProcedurePhoto1Remove,
    val procedurePhoto2Remove: ProcedurePhoto2Remove,
    val procedurePhoto3Remove: ProcedurePhoto3Remove,
    val onStepPhotoUriResult: OnStepPhotoUriResult,
    val onStepDescriptionChange: OnStepDescriptionChange,
    val onUpdateProcedure: OnUpdateProcedure
)