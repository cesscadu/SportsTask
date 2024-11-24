package com.kaizen.gaming.task.di

import com.kaizen.gaming.task.data.api.SportsApiService
import com.kaizen.gaming.task.data.database.dao.FavoritesDao
import com.kaizen.gaming.task.data.repository.SportsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideSportsRepository(api: SportsApiService, dao: FavoritesDao): SportsRepository {
        return SportsRepository(api, dao)
    }
}
