package ru.mulledwineapps.navigatorsample.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import ru.mulledwineapps.composenavigator.IAppState
import ru.mulledwineapps.composenavigator.NavRoute

object HomeRoute : NavRoute<HomeViewModel> {

    override val screen = "homeRoute"

    @Composable
    override fun viewModel(): HomeViewModel = hiltViewModel()

    @Composable
    override fun Content(
        appState: IAppState,
        backStackEntry: NavBackStackEntry,
        viewModel: HomeViewModel
    ) = HomeScreen(viewModel)
}
