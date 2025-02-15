package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.presentation.Screen_SelectCountry

sealed class SelectCountryScreenEvent {
    data object OnCancelClick:SelectCountryScreenEvent()
    data class OnQueryChange(val countryName:String):SelectCountryScreenEvent()
    data class OnCountryClick(val selectedCountry:String):SelectCountryScreenEvent()

}