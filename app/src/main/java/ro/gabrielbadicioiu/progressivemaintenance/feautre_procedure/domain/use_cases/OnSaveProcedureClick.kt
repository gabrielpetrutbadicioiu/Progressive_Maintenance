package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.CreateProcedureScreenErrState
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure

class OnSaveProcedureClick(
) {
     fun execute(
        procedure: Procedure,
        onEmptyName:(String)->Unit,
        onEmptyStep:(String)->Unit,
        onSuccess:(Procedure)->Unit):CreateProcedureScreenErrState
    {
        if (procedure.procedureName.isEmpty())
        {
            onEmptyName("Procedure name is required!")
            return CreateProcedureScreenErrState(isProcedureNameErr = true)
        }
        if (procedure.steps[0].stepDescription.isEmpty())
        {
            onEmptyStep("Add at least one step before saving the procedure.")
            return CreateProcedureScreenErrState(isStepDescriptionErr = true)
        }
        val cleanedList=procedure.steps.filter { step-> step.stepDescription.isNotEmpty() }
        val updatedProcedure= procedure.copy(steps = cleanedList)

        onSuccess(updatedProcedure)

        return CreateProcedureScreenErrState()
    }
}