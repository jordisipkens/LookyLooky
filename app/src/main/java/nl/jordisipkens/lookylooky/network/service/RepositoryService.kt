package nl.jordisipkens.lookylooky.network.service

import nl.jordisipkens.lookylooky.models.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryService {
    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String): Response<List<Repository>>
}