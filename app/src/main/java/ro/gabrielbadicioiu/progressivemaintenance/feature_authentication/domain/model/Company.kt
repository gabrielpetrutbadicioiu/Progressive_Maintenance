package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model

data class Company(
    val organisationName:String="",
    val industryType:String="",
    val country:String="",
    val companyLogoUrl:String="",
    val id:String="",
    val otp:String=(100000..999999).random().toString()
)
{
    fun toFirebaseDocument():HashMap<String, Any>
    {
        return hashMapOf(
            "organisationName" to organisationName,
            "industryType" to industryType,
            "country" to country,
            "companyLogoUrl" to companyLogoUrl,
            "id" to id,
            "otp" to otp
        )
    }
}
