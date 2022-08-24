package nl.jordisipkens.lookylooky.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity

@Dao
interface RepoDao {
    @Query("SELECT * FROM repoentity WHERE owner = :user")
    fun getAll(user: String): List<RepoEntity>

    @Query("SELECT * FROM repoentity WHERE name = :name")
    fun getRepoByName(name: String): List<RepoEntity>

    @Insert
    fun insertAll(repos: List<RepoEntity>)

    @Insert
    fun insertRepo(repos: RepoEntity)

    @Delete
    fun delete(repo: RepoEntity)
}