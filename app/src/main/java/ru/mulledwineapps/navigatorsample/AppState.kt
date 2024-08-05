package ru.mulledwineapps.navigatorsample

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController
import ru.mulledwineapps.composenavigator.IAppState

data class AppState(
    override val navController: NavHostController,
    override val snackbarHostState: SnackbarHostState
) : IAppState
