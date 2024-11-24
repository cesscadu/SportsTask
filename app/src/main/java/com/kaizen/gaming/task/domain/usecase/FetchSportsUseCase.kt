package com.kaizen.gaming.task.domain.usecase

import com.kaizen.gaming.task.data.repository.SportsRepository
import com.kaizen.gaming.task.domain.mapper.SportMapper
import javax.inject.Inject

class FetchSportsUseCase @Inject constructor(
    private val repository: SportsRepository
) {
    suspend operator fun invoke() = SportMapper().mapToSports(repository.fetchSports())
}
