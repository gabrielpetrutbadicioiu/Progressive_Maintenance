package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.core

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.Company

class OnCompanySearch {

    fun execute(
        searchedCompany:String,
        companyList:List<Company>
    ):List<Company>
    {
        return companyList.filter { company->
            company.organisationName.startsWith(searchedCompany, ignoreCase = true)
        }
    }
}