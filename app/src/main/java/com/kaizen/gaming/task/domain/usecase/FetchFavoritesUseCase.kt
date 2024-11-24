package com.kaizen.gaming.task.domain.usecase

import com.kaizen.gaming.task.data.repository.SportsRepository
import javax.inject.Inject

class FetchFavoritesUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend fun getAllFavorites(): List<String> {
        return repository.getAllFavorites().map { it.id }
    }
}