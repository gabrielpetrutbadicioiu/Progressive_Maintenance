package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm

class ClParameterNameChange {
    fun execute(clForm:CenterLineForm, index:Int, parameterName:String):CenterLineForm
    {val newParameterList=clForm.clParameterList.toMutableList()
        newParameterList[index]=clForm.clParameterList[index].copy(parameterName = parameterName)
        return clForm.copy(clParameterList = newParameterList.toList())

    }
}