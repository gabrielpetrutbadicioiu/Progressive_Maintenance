package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model

data class Company(
    val organisationName:String="",
    val industryType:String="",
    val country:String="",
    val companyLogoUrl:String=""
)
{
    fun toFirebaseDocument():HashMap<String, Any>
    {
        return hashMapOf(
            "organisationName" to organisationName,
            "industryType" to industryType,
            "country" to country,
            "companyLogoUrl" to companyLogoUrl
        )
    }
}
