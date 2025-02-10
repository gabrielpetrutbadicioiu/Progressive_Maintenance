package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_SelectCountry

import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry.Category

class OnQueryChange {
    fun execute(
        query:String,
        onQueryChange:(List<String>)->Unit,
        countryList: List<Category>
    )
    {
        var filteredCountries:List<String> = emptyList()
        var fullList:List<String> = emptyList()

        countryList.forEach { category ->
            category.items.forEach { country->
                fullList=fullList+country
            }
       filteredCountries= fullList.filter { country->
                country.startsWith(query, ignoreCase = true)
            }
        }
        onQueryChange(filteredCountries)
    }
}