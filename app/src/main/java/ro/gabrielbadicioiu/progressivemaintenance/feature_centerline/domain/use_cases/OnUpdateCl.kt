package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CreateClErrorState

class OnUpdateCl(
    private val companiesRepository: CompaniesRepository
) {
    suspend fun execute(
        clForm: CenterLineForm,
        onFailure:(String)->Unit,
        companyId:String,
        productionLineId:String,
        onSuccess:()->Unit
    ): CreateClErrorState
    {
        if (clForm.clName.isEmpty())
        {
            onFailure("Centerline form name required!")
            return CreateClErrorState(isClNameErr = true, errMsg = "Centerline form name required!")
        }
        if (clForm.clParameterList[0].parameterName.isEmpty() && clForm.clParameterList[0].parameterValue.isEmpty())
        {
            onFailure("At least one parameter must be provided!")
            return CreateClErrorState(isParameterErr = true, errMsg = "At least one parameter must be provided!")
        }

        companiesRepository.updateCl(
            companyId = companyId,
            lineId = productionLineId,
            onSuccess = {onSuccess()},
            onFailure = {e-> onFailure(e)},
            cl = clForm.copy(),
            clId = clForm.clFormId
        )
        return CreateClErrorState()

    }
}