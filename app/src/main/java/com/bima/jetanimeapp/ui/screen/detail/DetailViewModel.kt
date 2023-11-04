package com.bima.jetanimeapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.jetanimeapp.data.AnimeRepository
import com.bima.jetanimeapp.model.AnimeItem
import com.bima.jetanimeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AnimeItem>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<AnimeItem>> get() = _uiState

    fun getAnimeById(animeId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getAnimeItemById(animeId))
        }
    }

    fun addToFavorites(animeId: String) {
        viewModelScope.launch {
            repository.addToFavorites(animeId)
        }
    }

    fun removeFromFavorite(animeId: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(animeId)
        }
    }

    fun checkFavorite(animeId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = repository.isFavorite(animeId)
            onResult(isFavorite)
        }
    }
}