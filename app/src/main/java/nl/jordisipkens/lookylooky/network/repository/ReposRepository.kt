package nl.jordisipkens.lookylooky.network.repository

import nl.jordisipkens.lookylooky.persistence.entities.Repo
import nl.jordisipkens.lookylooky.network.NetworkHelper
import nl.jordisipkens.lookylooky.network.service.RepositoryService
import nl.jordisipkens.lookylooky.persistence.databases.LookyDatabase
import javax.inject.Inject

class ReposRepository @Inject constructor(private val db: LookyDatabase) {

    suspend fun fetchRepos(user: String): List<Repo>? {
        val repoDao = db.repoDao()
        val repos = repoDao.getAll()

        return repos.ifEmpty {
            val retro = NetworkHelper.getRetrofitInstance().create(RepositoryService::class.java)

            val response = retro.getRepos(user)
            println("Fetching repositories from GitHub")

            if (response.isSuccessful) {
                repoDao.insertAll(response.body()!!)
                response.body()!!
            } else {
                null
            }
        }
    }
}