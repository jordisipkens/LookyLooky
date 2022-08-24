package nl.jordisipkens.lookylooky.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM evententity WHERE repo_id = :repoId")
    fun getAllByRepo(repoId: Int): List<EventEntity>

    @Insert
    fun insertAll(repos: List<EventEntity>)

    @Insert
    fun insertRepo(repos: EventEntity)

    @Delete
    fun delete(repo: EventEntity)
}