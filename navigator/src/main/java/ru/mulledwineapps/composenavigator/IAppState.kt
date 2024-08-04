package ru.mulledwineapps.composenavigator

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavHostController

interface IAppState {
    val navController: NavHostController
    val snackbarHostState: SnackbarHostState
}
