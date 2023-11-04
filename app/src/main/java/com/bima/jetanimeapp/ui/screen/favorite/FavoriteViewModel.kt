package com.bima.jetanimeapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.jetanimeapp.data.AnimeRepository
import com.bima.jetanimeapp.model.AnimeItem
import com.bima.jetanimeapp.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<AnimeItem>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<AnimeItem>>> get() = _uiState

    val favoriteAnimes: Flow<List<AnimeItem>> = repository.getFavoriteAnimes()

    fun getAllFavoriteAnimes() {
        viewModelScope.launch {
            repository.getFavoriteAnimes()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteAnimeItems ->
                    _uiState.value = UiState.Success(favoriteAnimeItems)
                }
        }
    }
}