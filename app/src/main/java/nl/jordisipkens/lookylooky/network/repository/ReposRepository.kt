package nl.jordisipkens.lookylooky.network.repository

import nl.jordisipkens.lookylooky.models.Repository
import nl.jordisipkens.lookylooky.network.NetworkHelper
import nl.jordisipkens.lookylooky.network.service.RepositoryService

class ReposRepository(val user: String) {

    suspend fun fetchRepos(): List<Repository>? {
        val repo = NetworkHelper.getRetrofitInstance().create(RepositoryService::class.java)

        val response = repo.getRepos(user)

        return if(response.isSuccessful) {
            response.body()!!
        } else {
            null
        }

    }
}