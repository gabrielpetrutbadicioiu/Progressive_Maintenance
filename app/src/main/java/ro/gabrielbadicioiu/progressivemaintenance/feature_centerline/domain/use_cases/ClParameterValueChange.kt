package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm

class ClParameterValueChange {
    fun execute(clForm:CenterLineForm, index:Int, parameterValue:String):CenterLineForm
    {
        val newClParameterList=clForm.clParameterList.toMutableList()
        newClParameterList[index]=clForm.clParameterList[index].copy(parameterValue = parameterValue)
        return clForm.copy(clParameterList = newClParameterList.toList())
    }
}