package ru.mulledwineapps.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import timber.log.Timber

/**
 * A route the app can navigate to.
 */
interface NavRoute<T : RouteNavigator> {

    val screen: String

    val route: String
        get() {
            val nullable = getArguments().indexOfFirst { it.argument.isNullable }
            return screen + getArguments().mapIndexed { index, arg ->
                when {
                    nullable == -1 -> "/{${arg.name}}"
                    index == nullable -> "?${arg.name}={${arg.name}}"
                    index > nullable -> "&${arg.name}={${arg.name}}"
                    else -> "/{${arg.name}}"
                }
            }.joinToString("")
        }

    /**
     * Returns the screen's content.
     */
    @Composable
    fun Content(appState: IAppState, backStackEntry: NavBackStackEntry, viewModel: T)

    /**
     * Returns the screen's ViewModel. Needs to be overridden so that Hilt can generate code for the factory for the ViewModel class.
     */
    @Composable
    fun viewModel(): T

    /**
     * Override when this page uses arguments.
     *
     * We do it here and not in the [NavigationComponent to keep it centralized]
     */
    fun getArguments(): List<NamedNavArgument> = emptyList()

    fun getDeepLinks(): List<NavDeepLink> = emptyList()

    /**
     * Generates the composable for this route.
     */
    fun composable(
        builder: NavGraphBuilder,
        appState: IAppState
    ) {
        builder.composable(route, getArguments(), getDeepLinks()) { backStackEntry ->

            val viewModel = viewModel()
            val viewStateAsState by viewModel.navigationState.collectAsState()

            LaunchedEffect(viewStateAsState) {
                Timber.tag("navigator").d("navigate to $viewStateAsState")
                updateNavigationState(
                    appState.navController,
                    viewStateAsState,
                    viewModel::onNavigated
                )
            }

            Content(appState, backStackEntry, viewModel)
        }
    }

    companion object {
        /**
         * Navigates to [navigationState].
         */
        fun updateNavigationState(
            navHostController: NavHostController,
            navigationState: NavigationState,
            onNavigated: (navState: NavigationState) -> Unit,
        ) {
            when (navigationState) {
                is NavigationState.NavigateToRoute -> {
                    navHostController.navigate(navigationState.route, navigationState.navOptions)
                    onNavigated(navigationState)
                }

                is NavigationState.PopToRoute -> {
                    navHostController.popBackStack(navigationState.route, false)
                    onNavigated(navigationState)
                }

                is NavigationState.PopUpAndNavigate -> {
                    navHostController.navigateUp()
                    navHostController.navigate(navigationState.route)
                    onNavigated(navigationState)
                }

                is NavigationState.NavigateUp -> {
                    navHostController.navigateUp()
                    onNavigated(navigationState)
                }

                is NavigationState.Idle -> {
                }
            }
        }
    }
}

fun <T> SavedStateHandle.getOrThrow(key: String): T =
    get<T>(key) ?: throw IllegalArgumentException(
        "Mandatory argument $key missing in arguments."
    )
