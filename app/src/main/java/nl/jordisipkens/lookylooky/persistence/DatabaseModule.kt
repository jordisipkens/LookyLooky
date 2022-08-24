package nl.jordisipkens.lookylooky.persistence

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.jordisipkens.lookylooky.persistence.dao.EventDao
import nl.jordisipkens.lookylooky.persistence.dao.RepoDao
import nl.jordisipkens.lookylooky.persistence.databases.LookyDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideRepoDao(lookyDatabase: LookyDatabase): RepoDao {
        return lookyDatabase.repoDao()
    }

    @Provides
    fun provideEventDao(lookyDatabase: LookyDatabase): EventDao {
        return lookyDatabase.eventDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : LookyDatabase {
        return Room.databaseBuilder(
            context,
            LookyDatabase::class.java,
            "Looky-Database")
            .fallbackToDestructiveMigration()
            .build()
    }
}