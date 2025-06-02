package ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.Procedure
import ro.gabrielbadicioiu.progressivemaintenance.feautre_procedure.domain.model.ProcedureStep

class OnStepDescriptionChange {
    fun execute(
        procedure: Procedure,
        index:Int,
        description:String
    ):Procedure
    {
        val step=procedure.steps[index].copy(stepDescription = description)
        val stepList=procedure.steps.toMutableList()
        stepList[index]=step
        return procedure.copy(steps = stepList)
    }
}