package com.bima.jetanimeapp.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel

@Composable
fun HomeScreenMenu(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = ViewModel(
        factory = ViewModelFactory(Injection.provideRepository))
    ),
    navigateToDetail: (String) -> Unit
) {
    val searchResult by viewModel.searchResult.collectAsState(initial = emptyList())
    val query by viewModel.query.collectAsState(initial = "")

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllAnimes()
            }


        }
    }
}