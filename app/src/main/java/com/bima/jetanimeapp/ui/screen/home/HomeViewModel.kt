package com.bima.jetanimeapp.ui.screen.home

import androidx.lifecycle.ViewModel
import com.bima.jetanimeapp.data.AnimeRepository
import com.bima.jetanimeapp.model.AnimeItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Map<Char, List<AnimeItem>>>> =
        MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<Map<Char, List<AnimeItem>>>> get() = _uiState

    private val _searchResult = MutableStateFlow<List<AnimeItem>>(emptyList())
    val searchResult: StateFlow<List<AnimeItem>> get() = _searchResult


    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query
}