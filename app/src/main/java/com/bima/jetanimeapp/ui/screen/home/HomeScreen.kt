package com.bima.jetanimeapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

@Composable
fun HomeScreen(
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

            is UiState.Success -> {
                Coloumn {
                    SearchBar(
                        query = query,
                        onQueryChange = { newQuery ->
                            viewModel.setQuery(newQuery)
                            viewModel.searchAnimes()
                        },
                        modifier = Modifier.background(MaterialTheme.colorScheme.primary)
                    )
                    HomeContent(
                        groupedAnimes = if (query.isEmpty()) uiState.data else emptyMap(),
                        searchResult = searchResult,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }
            }

            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    groupedAnimes: Map<Char, List<AnimesItem>>,
    searchResult: List<AnimesItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
) {
    Box(
        modifier = modifier.padding(8.dp)
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp),
            modifier = modifier.testTag("AnimesList")
        ) {
            if (searchResult.isNotEmpty()) {
                item(searchResult, key = { it.item.id }) { data ->
                    AnimeListItem(
                        animeName = data.item.animeName,
                        bannerUrl = data.item.bannerUrl,
                        modifier = Modifier.clickable {
                            navigateToDetail(data.item.data)
                        }
                    )
                }
            } else {
                groupedAnimes.entries.forEach { (_, animeItems) ->
                    items(animeItems) { data ->
                        AnimeListItem(
                            animeName = data.item.animeName,
                            bannerUrl = data.item.bannerUrl,
                            modifier = Modifier.clickable {
                                navigateToDetail(data.item.id)
                            }
                        )
                    }
                }
            }
        }
    }
}