package com.bima.jetanimeapp.data

import com.bima.jetanimeapp.model.AnimeItem
import com.bima.jetanimeapp.model.AnimesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AnimeRepository {

    private val animeItem = mutableListOf<AnimeItem>()
    private val favoriteAnimes = mutableListOf<String>()

    init {
        if (animeItem.isEmpty()) {
            AnimesData.anime.forEach {
                animeItem.add(AnimeItem(it, 0))
            }
        }
    }

    fun getSortedAndGroupedAnime(): Flow<Map<Char, List<AnimeItem>>> {
        return flow {
            val filteredAnimes = animeItem.filter {
                it.item.animeName.contains(query, ignoreCase = true)
            }
            emit(filteredAnimes)
        }
    }

    fun getFavoriteAnimes(): Flow<List<AnimeItem>> {
        return flow {
            val favoriteAnimeItems = animeItem.filter { it.item.id in favoriteAnimes }
            emit(favoriteAnimeItems)
        }
    }

    fun addToFavorites(animeId: String) {
        if (!favoriteAnimes.contains(animeId)) {
            favoriteAnimes.add(animeId)
        }
    }

    fun removeFromFavorites(animeId: String) {
        favoriteAnimes.remove(animeId)
    }

    fun isFavorite(animeId: String): Boolean {
        return favoriteAnimes.contains(animeId)
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository = instance ?: synchronized(this) {
            AnimeRepository().apply {
                instance = this
            }
        }
    }
}