package com.bima.jetanimeapp.navigation

sealed class Screen (val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object DetailAnime : Screen("home/{animeId") {
        fun createRoute(animeId: String) = "home/$animeId"
    }
}