package com.kaizen.gaming.task.usecase

import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.data.repository.SportsRepository
import com.kaizen.gaming.task.domain.usecase.ManageFavoritesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ManageFavoritesUseCaseTest {

    private lateinit var useCase: ManageFavoritesUseCase
    private val mockRepository: SportsRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        useCase = ManageFavoritesUseCase(mockRepository)
    }

    @Test
    fun `addFavorite should call repository addFavorite`() = runTest {
        val event = EventDetail("1", "Event 1", 1732374551, false)

        useCase.addFavorite(event)

        coVerify { mockRepository.addFavorite(any()) }
    }

    @Test
    fun `removeFavorite should call repository removeFavorite`() = runTest {
        val event = EventDetail("1", "Event 1", 1732374551, false)

        useCase.removeFavorite(event)

        coVerify { mockRepository.removeFavorite(any()) }
    }

    @Test
    fun `isFavorite should return true for a favorite event`() = runTest {
        val eventId = "1"

        coEvery { mockRepository.isFavorite(eventId) } returns true

        val result = useCase.isFavorite(eventId)

        assert(result)
    }

    @Test
    fun `isFavorite should return false for a non-favorite event`() = runTest {
        val eventId = "2"
        coEvery { mockRepository.isFavorite(eventId) } returns false

        val result = useCase.isFavorite(eventId)

        assert(!result)
    }
}