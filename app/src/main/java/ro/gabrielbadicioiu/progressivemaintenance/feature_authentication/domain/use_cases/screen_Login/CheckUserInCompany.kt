package ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.use_cases.screen_Login


import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.model.UserDetails
import ro.gabrielbadicioiu.progressivemaintenance.feature_authentication.domain.repository.CompaniesRepository

class CheckUserInCompany(
    private val companiesRepository: CompaniesRepository
) {
   suspend fun execute(
       companyID:String,
       userID:String,
       onSuccess:(UserDetails?)->Unit,
       onFailure:(String)->Unit,
       onUserNotFound:()->Unit
    )
    {

       companiesRepository.getUserInCompany(
                   companyID = companyID,
                   currentUserID = userID,
                   onSuccess = {userDetails ->
                    onSuccess(userDetails)
                   },
                   onFailure = {e->onFailure(e)},
                   onUserNotFound = {onUserNotFound()}
               )


//        val userRef=FirebaseFirestore.getInstance()
//            .collection(FirebaseCollections.COMPANIES)
//            .document(companyID)
//            .collection(FirebaseSubCollections.USERS)
//            .document(userID)
//        userRef.get().addOnSuccessListener {document->
//            if (document.exists())
//            {
//                onSuccess(true)
//            }
//            else{
//                onSuccess(false)
//            }
//        }
//            .addOnFailureListener {e->
//                onFailure(e.message ?: "Error checking user")
//            }
    }
}