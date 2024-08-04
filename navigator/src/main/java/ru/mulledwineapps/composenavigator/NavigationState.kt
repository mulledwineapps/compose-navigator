package ru.mulledwineapps.composenavigator

import androidx.compose.runtime.Immutable
import androidx.navigation.NavOptions
import java.util.UUID

@Immutable
sealed class NavigationState {
    data object Idle : NavigationState()

    data class NavigateToRoute(
        val route: String,
        val navOptions: NavOptions?,
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()

    data class PopToRoute(
        val route: String,
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()

    data class PopUpAndNavigate(
        val route: String,
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()

    data class NavigateUp(
        val id: String = UUID.randomUUID().toString()
    ) : NavigationState()
}
