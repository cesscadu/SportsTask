package com.kaizen.gaming.task.di

import android.content.Context
import androidx.room.Room
import com.kaizen.gaming.task.data.database.SportsDatabase
import com.kaizen.gaming.task.data.database.dao.FavoritesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): SportsDatabase {
        return Room.databaseBuilder(
            appContext,
            SportsDatabase::class.java,
            "sports_database"
        ).build()
    }

    @Provides
    fun provideFavoritesDao(database: SportsDatabase): FavoritesDao {
        return database.favoritesDao()
    }
}
