package nl.jordisipkens.lookylooky.network.repository

import nl.jordisipkens.lookylooky.network.NetworkHelper
import nl.jordisipkens.lookylooky.network.service.EventService
import nl.jordisipkens.lookylooky.persistence.databases.LookyDatabase
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity
import nl.jordisipkens.lookylooky.persistence.entities.toEntity
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val db: LookyDatabase
){
    suspend fun fetchEvents(user: String, repo: RepoEntity): List<EventEntity>? {
        val eventDao = db.eventDao()
        val events = eventDao.getAllByRepo(repo.id)

        return events.ifEmpty {
            val retro = NetworkHelper.getRetrofitInstance().create(EventService::class.java)

            val response = retro.getEvents(user, repo.name)

            if(response.isSuccessful) {
                val eventEntities = response.body()!!.map {
                    it.toEntity(repo.id)
                }
                eventDao.insertAll(eventEntities)
                eventEntities
            } else {
                null
            }
        }
    }
}