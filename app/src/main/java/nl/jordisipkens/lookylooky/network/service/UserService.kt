package nl.jordisipkens.lookylooky.network.service

import nl.jordisipkens.lookylooky.models.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{user}")
    suspend fun getUser(@Path("user") user: String): User
}