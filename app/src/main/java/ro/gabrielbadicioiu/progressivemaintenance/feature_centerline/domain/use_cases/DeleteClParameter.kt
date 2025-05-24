package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineParameter

class DeleteClParameter {
    fun execute(clForm:CenterLineForm, index:Int):CenterLineForm
    {
        return clForm.copy(clParameterList = clForm.clParameterList-clForm.clParameterList[index])

    }
}