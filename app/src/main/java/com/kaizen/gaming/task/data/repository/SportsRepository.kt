package com.kaizen.gaming.task.data.repository

import com.kaizen.gaming.task.data.api.SportsApiService
import com.kaizen.gaming.task.data.database.dao.FavoritesDao
import com.kaizen.gaming.task.data.database.entity.EventEntity
import com.kaizen.gaming.task.domain.model.remote.SportResponse
import javax.inject.Inject

class SportsRepository @Inject constructor(
    private val api: SportsApiService,
    private val favoritesDao: FavoritesDao
) {
    suspend fun fetchSports(): List<SportResponse> {
        return api.getSports()
    }

    suspend fun addFavorite(event: EventEntity) {
        favoritesDao.addFavorite(event)
    }

    suspend fun removeFavorite(event: EventEntity) {
        favoritesDao.removeFavorite(event)
    }

    suspend fun getAllFavorites(): List<EventEntity> {
        return favoritesDao.getAllFavorites()
    }

    suspend fun isFavorite(eventId: String): Boolean {
        return favoritesDao.getFavorite(eventId) != null
    }
}
