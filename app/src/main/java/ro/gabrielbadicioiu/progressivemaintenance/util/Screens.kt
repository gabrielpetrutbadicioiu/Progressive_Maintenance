package ro.gabrielbadicioiu.progressivemaintenance.util

import kotlinx.serialization.Serializable

sealed class Screens()
{
@Serializable
object LoginScreen:Screens()

    @Serializable
object SignUpScreen:Screens()
}
