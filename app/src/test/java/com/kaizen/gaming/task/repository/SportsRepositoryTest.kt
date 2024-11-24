package com.kaizen.gaming.task.repository

import com.kaizen.gaming.task.data.api.SportsApiService
import com.kaizen.gaming.task.data.database.dao.FavoritesDao
import com.kaizen.gaming.task.data.database.entity.EventEntity
import com.kaizen.gaming.task.data.repository.SportsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SportsRepositoryTest {

    private val mockApi: SportsApiService = mockk(relaxed = true)
    private val mockFavoritesDao: FavoritesDao = mockk(relaxed = true)
    private lateinit var repository: SportsRepository

    @Before
    fun setup() {
        repository = SportsRepository(api = mockApi, favoritesDao = mockFavoritesDao)
    }

    @Test
    fun `addFavorite should call FavoritesDao addFavorite`() = runTest {
        val event = EventEntity("1", "Match 1", 1732374551)

        repository.addFavorite(event)

        coVerify { mockFavoritesDao.addFavorite(event) }
    }

    @Test
    fun `removeFavorite should call FavoritesDao removeFavorite`() = runTest {
        val event = EventEntity("1", "Match 1", 1732374551)

        repository.removeFavorite(event)

        coVerify { mockFavoritesDao.removeFavorite(event) }
    }

    @Test
    fun `getAllFavorites should return list of favorite events`() = runTest {
        val mockFavorites = listOf(
            EventEntity("1", "Match 1", 1732374551),
            EventEntity("2", "Match 2", 1732375551)
        )
        coEvery { mockFavoritesDao.getAllFavorites() } returns mockFavorites

        val result = repository.getAllFavorites()

        assert(result == mockFavorites)
    }
}
