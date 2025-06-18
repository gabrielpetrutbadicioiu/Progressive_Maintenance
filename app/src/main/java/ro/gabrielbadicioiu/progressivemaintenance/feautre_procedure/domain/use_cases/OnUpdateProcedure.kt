package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.CreateProcedureScreenErrState
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.presentation.screen_editProcedure.EditProcedureUiErrState


class OnUpdateProcedure(
) {
    fun execute(
        procedure: Procedure,
        onEmptyName:(String)->Unit,
        onEmptyStep:(String)->Unit,
        onSuccess:(Procedure)->Unit): EditProcedureUiErrState
    {
        if (procedure.procedureName.isEmpty())
        {
            onEmptyName("Procedure name is required!")
            return EditProcedureUiErrState(isEmptyProcedureNameErr = true)
        }
        if (procedure.steps[0].stepDescription.isEmpty())
        {
            onEmptyStep("Add at least one step before saving the procedure.")
            return EditProcedureUiErrState(isStepDescriptionErr = true)
        }
        val cleanedList=procedure.steps.filter { step-> step.stepDescription.isNotEmpty() }
        val updatedProcedure= procedure.copy(steps = cleanedList)

        onSuccess(updatedProcedure)

        return EditProcedureUiErrState()
    }
}