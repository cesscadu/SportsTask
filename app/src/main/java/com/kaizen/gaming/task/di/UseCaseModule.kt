package com.kaizen.gaming.task.di

import com.kaizen.gaming.task.data.repository.SportsRepository
import com.kaizen.gaming.task.domain.usecase.FetchFavoritesUseCase
import com.kaizen.gaming.task.domain.usecase.FetchSportsUseCase
import com.kaizen.gaming.task.domain.usecase.ManageFavoritesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideFetchSportsUseCase(repository: SportsRepository): FetchSportsUseCase {
        return FetchSportsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFetchFavoritesUseCase(repository: SportsRepository): FetchFavoritesUseCase {
        return FetchFavoritesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideManageFavoritesUseCase(repository: SportsRepository): ManageFavoritesUseCase {
        return ManageFavoritesUseCase(repository)
    }
}
