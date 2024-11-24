package com.kaizen.gaming.task.viewmodel

import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.domain.model.local.SportDetail
import com.kaizen.gaming.task.domain.model.local.Sports
import com.kaizen.gaming.task.domain.usecase.FetchFavoritesUseCase
import com.kaizen.gaming.task.domain.usecase.FetchSportsUseCase
import com.kaizen.gaming.task.domain.usecase.ManageFavoritesUseCase
import com.kaizen.gaming.task.ui.viewmodel.SportsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    private lateinit var fetchSportsUseCase: FetchSportsUseCase
    private lateinit var manageFavoritesUseCase: ManageFavoritesUseCase
    private lateinit var fetchFavoritesUseCase: FetchFavoritesUseCase
    private lateinit var viewModel: SportsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        fetchSportsUseCase = mockk()
        manageFavoritesUseCase = mockk(relaxed = true)
        fetchFavoritesUseCase = mockk {
            coEvery { getAllFavorites() } returns emptyList()
        }

        viewModel = SportsViewModel(
            fetchSportsUseCase,
            manageFavoritesUseCase,
            fetchFavoritesUseCase,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `fetchSports updates sports state flow`() = runTest {
        val mockSports = listOf(
            Sports(SportDetail("FOOT", ""), "Football", emptyList()),
            Sports(SportDetail("BASK", ""), "Basketball", emptyList())
        )
        coEvery { fetchSportsUseCase() } returns mockSports

        viewModel.fetchSports()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockSports, viewModel.sports.first())
        assertEquals(false, viewModel.isLoading.first())
        coVerify { fetchSportsUseCase() }
    }

    @Test
    fun `toggleFavorite adds and removes favorites correctly`() = runTest {
        val mockEvent = EventDetail("event1", "Match 1", 1732374551, false)

        viewModel.toggleFavorite(mockEvent)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf("event1"), viewModel.favorites.first())
        coVerify { manageFavoritesUseCase.addFavorite(mockEvent) }

        viewModel.toggleFavorite(mockEvent)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(emptyList<String>(), viewModel.favorites.first())
        coVerify { manageFavoritesUseCase.removeFavorite(mockEvent) }
    }

    @Test
    fun `loadFavorites updates favorites state flow`() = runTest {
        val mockFavorites = listOf("event1", "event2")
        coEvery { fetchFavoritesUseCase.getAllFavorites() } returns mockFavorites

        viewModel.loadFavorites()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockFavorites, viewModel.favorites.first())
        coVerify { fetchFavoritesUseCase.getAllFavorites() }
    }

    @Test
    fun `isFavorite returns true for favorite event`() = runTest {
        val mockFavorites = listOf("event1", "event2")
        coEvery { fetchFavoritesUseCase.getAllFavorites() } returns mockFavorites

        viewModel.loadFavorites()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.isFavorite("event1"))
        assert(!viewModel.isFavorite("event3"))
    }

    @Test
    fun `toggleShowFavorites filters and restores events correctly`() = runTest {
        val mockEvent1 = EventDetail("event1", "Match 1", 1732374551, isFavorite = true)
        val mockEvent2 = EventDetail("event2", "Match 2", 1732375551, isFavorite = false)
        val mockSports = listOf(
            Sports(
                identifier = "FOOT",
                detail = SportDetail("Football", "⚽"),
                events = listOf(mockEvent1, mockEvent2),
                originalEvents = listOf(mockEvent1, mockEvent2)
            )
        )
        coEvery { fetchSportsUseCase() } returns mockSports
        coEvery { fetchFavoritesUseCase.getAllFavorites() } returns listOf("event1")

        viewModel.fetchSports()
        viewModel.loadFavorites()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.toggleShowFavorites("FOOT")
        testDispatcher.scheduler.advanceUntilIdle()

        val filteredSports = viewModel.sports.first()
        assertEquals(1, filteredSports[0].events.size)
        assertEquals("event1", filteredSports[0].events[0].id)

        viewModel.toggleShowFavorites("FOOT")
        testDispatcher.scheduler.advanceUntilIdle()

        val allEvents = viewModel.sports.first()[0].events
        assertEquals(2, allEvents.size)
        assert(allEvents.any { it.id == "event1" })
        assert(allEvents.any { it.id == "event2" })
    }

}

