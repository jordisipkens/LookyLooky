package nl.jordisipkens.lookylooky.network.service

import nl.jordisipkens.lookylooky.network.models.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EventService {
    @GET("repos/{user}/{repo}/events")
    suspend fun getEvents(@Path("user") user: String, @Path("repo") repo: String): Response<List<Event>>
}