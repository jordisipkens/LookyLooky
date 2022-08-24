package nl.jordisipkens.lookylooky.persistence.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.jordisipkens.lookylooky.persistence.dao.RepoDao
import nl.jordisipkens.lookylooky.persistence.entities.Repo

@Database(entities = [Repo::class], version = 3, exportSchema = false)
abstract class LookyDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
}