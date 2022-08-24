package nl.jordisipkens.lookylooky.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.jordisipkens.lookylooky.persistence.entities.Repo

@Dao
interface RepoDao {
    @Query("SELECT * FROM repo")
    fun getAll(): List<Repo>

    @Insert
    fun insertAll(repos: List<Repo>)

    @Insert
    fun insertRepo(repos: Repo)

    @Delete
    fun delete(repo: Repo)
}