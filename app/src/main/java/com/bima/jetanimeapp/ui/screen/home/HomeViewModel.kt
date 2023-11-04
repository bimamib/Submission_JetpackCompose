package com.bima.jetanimeapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bima.jetanimeapp.data.AnimeRepository
import com.bima.jetanimeapp.model.AnimeItem
import com.bima.jetanimeapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Map<Char, List<AnimeItem>>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Map<Char, List<AnimeItem>>>> get() = _uiState

    private val _searchResult = MutableStateFlow<List<AnimeItem>>(emptyList())
    val searchResult: StateFlow<List<AnimeItem>> get() = _searchResult

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    fun getAllAnimes() {
        viewModelScope.launch {
            repository.getSortedAndGroupedAnime()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { groupedAnimeItems ->
                    _uiState.value = UiState.Success(groupedAnimeItems)
                }
        }
    }

    fun searchAnimes() {
        val currentQuery = _query.value
        viewModelScope.launch {
            repository.searchAnimes(currentQuery)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { searchResult ->
                    _searchResult.value = searchResult
                }
        }
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }
}

