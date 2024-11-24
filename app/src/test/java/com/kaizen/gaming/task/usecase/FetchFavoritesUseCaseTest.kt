package com.kaizen.gaming.task.usecase

import com.kaizen.gaming.task.data.database.entity.EventEntity
import com.kaizen.gaming.task.data.repository.SportsRepository
import com.kaizen.gaming.task.domain.usecase.FetchFavoritesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchFavoritesUseCaseTest {

    private lateinit var useCase: FetchFavoritesUseCase
    private val mockRepository: SportsRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        useCase = FetchFavoritesUseCase(mockRepository)
    }

    @Test
    fun `getAllFavorites should return a list of favorite event IDs`() = runTest {
        val mockFavorites = listOf(
            EventEntity("1", "Event 1", 1732374551),
            EventEntity("2", "Event 2", 1732375551)
        )
        coEvery { mockRepository.getAllFavorites() } returns mockFavorites

        val result = useCase.getAllFavorites()

        assert(result == listOf("1", "2"))
    }
}
