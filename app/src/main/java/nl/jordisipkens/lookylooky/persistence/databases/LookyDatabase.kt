package nl.jordisipkens.lookylooky.persistence.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import nl.jordisipkens.lookylooky.persistence.dao.EventDao
import nl.jordisipkens.lookylooky.persistence.dao.RepoDao
import nl.jordisipkens.lookylooky.persistence.entities.EventEntity
import nl.jordisipkens.lookylooky.persistence.entities.RepoEntity

@Database(entities = [RepoEntity::class, EventEntity::class], version = 8, exportSchema = false)
abstract class LookyDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun eventDao(): EventDao
}