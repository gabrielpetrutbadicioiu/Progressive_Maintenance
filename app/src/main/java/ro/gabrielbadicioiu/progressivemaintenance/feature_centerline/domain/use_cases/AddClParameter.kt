package ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.use_cases

import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineForm
import ro.gabrielbadicioiu.progressivemaintenance.feature_centerline.domain.model.CenterLineParameter

class AddClParameter {
    fun execute(clForm:CenterLineForm):CenterLineForm
    {
        val newList=clForm.clParameterList+CenterLineParameter()
        return clForm.copy(clParameterList = newList)
    }
}