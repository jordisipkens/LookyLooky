package nl.jordisipkens.lookylooky.network.repository

import nl.jordisipkens.lookylooky.network.NetworkHelper
import nl.jordisipkens.lookylooky.network.service.RepositoryService
import nl.jordisipkens.lookylooky.persistence.databases.LookyDatabase
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import nl.jordisipkens.lookylooky.persistence.entities.toEntity
import javax.inject.Inject

class ReposRepository @Inject constructor(private val db: LookyDatabase) {

    suspend fun fetchRepos(user: String): List<RepoEntity>? {
        val repoDao = db.repoDao()
        val repos = repoDao.getAll(user)

        return repos.ifEmpty {
            val retro = NetworkHelper.getRetrofitInstance().create(RepositoryService::class.java)

            val response = retro.getRepos(user)
            println("Fetching repositories from GitHub")

            if (response.isSuccessful) {
                val repoEntities = response.body()!!.map {
                    it.toEntity()
                }
                repoDao.insertAll(repoEntities)
                repoEntities
            } else {
                null
            }
        }
    }
}