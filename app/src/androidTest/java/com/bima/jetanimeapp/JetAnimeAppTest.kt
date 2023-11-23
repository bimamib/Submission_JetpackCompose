package com.bima.jetanimeapp

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.bima.jetanimeapp.model.AnimesData
import com.bima.jetanimeapp.navigation.Screen
import com.bima.jetanimeapp.ui.theme.JetAnimeAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class JetAnimeAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            JetAnimeAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                JetAnimeApp(navController = navController)
            }
        }
    }

    @Test
    fun navHostVerifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHostClickItemNavigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("AnimeList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(AnimesData.anime[5].animeName).performClick()
        navController.assertCurrentRouteName(Screen.DetailAnime.route)
        composeTestRule.onNodeWithText(AnimesData.anime[5].animeName).assertIsDisplayed()
    }

    @Test
    fun navHostClickItemNavigatesBack() {
        composeTestRule.onNodeWithTag("AnimeList").performScrollToIndex(5)
        composeTestRule.onNodeWithText(AnimesData.anime[5].animeName).performClick()
        navController.assertCurrentRouteName(Screen.DetailAnime.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back))
            .performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHostBottomNavigationWorking() {
        composeTestRule.onNodeWithStringId(R.string.menu_favorite).performClick()
        navController.assertCurrentRouteName(Screen.Favorite.route)
        composeTestRule.onNodeWithStringId(R.string.menu_about).performClick()
        navController.assertCurrentRouteName(Screen.About.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}