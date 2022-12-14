package nl.jordisipkens.lookylooky.network.service

import nl.jordisipkens.lookylooky.network.models.Repo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryService {
    @GET("users/{user}/repos?per_page=50")
    suspend fun getRepos(@Path("user") user: String): Response<List<Repo>>
}