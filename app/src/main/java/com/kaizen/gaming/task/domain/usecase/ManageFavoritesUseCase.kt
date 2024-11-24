package com.kaizen.gaming.task.domain.usecase

import com.kaizen.gaming.task.data.database.entity.EventEntity
import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.data.repository.SportsRepository
import javax.inject.Inject

class ManageFavoritesUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend fun addFavorite(event: EventDetail) {
        repository.addFavorite(
            EventEntity(
                id = event.id,
                shortDescription = event.shortDescription,
                timestamp = event.timestamp
            )
        )
    }

    suspend fun removeFavorite(event: EventDetail) {
        repository.removeFavorite(
            EventEntity(
                id = event.id,
                shortDescription = event.shortDescription,
                timestamp = event.timestamp
            )
        )
    }

    suspend fun isFavorite(eventId: String): Boolean {
        return repository.isFavorite(eventId)
    }
}