package com.kaizen.gaming.task.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaizen.gaming.task.di.IoDispatcher
import com.kaizen.gaming.task.domain.model.local.EventDetail
import com.kaizen.gaming.task.domain.model.local.Sports
import com.kaizen.gaming.task.domain.usecase.FetchFavoritesUseCase
import com.kaizen.gaming.task.domain.usecase.FetchSportsUseCase
import com.kaizen.gaming.task.domain.usecase.ManageFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SportsViewModel @Inject constructor(
    private val fetchSportsUseCase: FetchSportsUseCase,
    private val manageFavoritesUseCase: ManageFavoritesUseCase,
    private val fetchFavoritesUseCase: FetchFavoritesUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _sports = MutableStateFlow<List<Sports>>(emptyList())
    val sports: StateFlow<List<Sports>> get() = _sports

    private val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchSports() {
        viewModelScope.launch(ioDispatcher) {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                _sports.value = fetchSportsUseCase()
            } catch (e: Exception) {
                _errorMessage.value = "Something went wrong, please try again"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch(ioDispatcher) {
            _favorites.value = fetchFavoritesUseCase.getAllFavorites()
        }
    }

    fun toggleFavorite(event: EventDetail) {
        viewModelScope.launch(ioDispatcher) {
            if (_favorites.value.contains(event.id)) {
                _favorites.value -= event.id
                manageFavoritesUseCase.removeFavorite(event)
            } else {
                _favorites.value += event.id
                manageFavoritesUseCase.addFavorite(event)
            }
            _sports.value = _sports.value.map { sport ->
                sport.copy(events = sport.events.map {
                    if (it.id == event.id) it.copy(isFavorite = _favorites.value.contains(it.id)) else it
                })
            }
        }
    }

    fun isFavorite(eventId: String): Boolean {
        return _favorites.value.contains(eventId)
    }

    fun toggleShowFavorites(identifier: String) {
        viewModelScope.launch(ioDispatcher) {
            _sports.value = _sports.value.map { sport ->
                if (sport.identifier == identifier) {
                    if (sport.showFavoritesOnly) {
                        sport.copy(
                            showFavoritesOnly = false,
                            events = sport.originalEvents
                        )
                    } else {
                        sport.copy(
                            showFavoritesOnly = true,
                            events = sport.originalEvents.filter { _favorites.value.contains(it.id) }
                        )
                    }
                } else {
                    sport
                }
            }
        }
    }
}
