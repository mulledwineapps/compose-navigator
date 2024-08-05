package ru.mulledwineapps.navigatorsample

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ru.mulledwineapps.navigatorsample.home.HomeRoute

@Composable
fun NavigatorApp(modifier: Modifier = Modifier) {
    val appState = AppState(
        navController = rememberNavController(),
        snackbarHostState = remember { SnackbarHostState() }
    )
    NavHost(
        navController = appState.navController,
        modifier = modifier,
        startDestination = HomeRoute.screen,
    ) {
        HomeRoute.composable(this, appState)
    }
}
