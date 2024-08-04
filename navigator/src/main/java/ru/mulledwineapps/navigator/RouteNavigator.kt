package ru.mulledwineapps.navigator

import androidx.annotation.VisibleForTesting
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// https://medium.com/@ffvanderlaan/navigation-in-jetpack-compose-using-viewmodel-state-3b2517c24dde

/**
 * Navigator to use when initiating navigation from a ViewModel.
 */
interface RouteNavigator {
    fun onNavigated(state: NavigationState)
    fun navigateUp()
    fun popToRoute(route: String)
    fun popUpAndNavigate(route: String)
    fun navigateToRoute(route: String, singleTop: Boolean = false)
    fun clearAndNavigate(route: String)
    fun navigateTopLevel(route: String)

    val navigationState: StateFlow<NavigationState>
}

class SpRouteNavigator : RouteNavigator {

    /**
     * Note that I'm using a single state here, not a list of states. As a result, if you quickly
     * update the state multiple times, the view will only receive and handle the latest state,
     * which is fine for my use case.
     */
    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override fun onNavigated(state: NavigationState) {
        // clear navigation state, if state is the current state:
        navigationState.compareAndSet(state, NavigationState.Idle)
    }

    override fun popToRoute(route: String) = navigate(NavigationState.PopToRoute(route))

    override fun popUpAndNavigate(route: String) {
        navigate(NavigationState.PopUpAndNavigate(route = route))
    }

    override fun navigateUp() = navigate(NavigationState.NavigateUp())

    override fun navigateToRoute(route: String, singleTop: Boolean) {
        navigate(
            NavigationState.NavigateToRoute(
                route = route,
                navOptions = navOptions { launchSingleTop = singleTop }
            )
        )
    }

    override fun clearAndNavigate(route: String) {
        navigate(
            NavigationState.NavigateToRoute(
                route = route,
                navOptions = navOptions {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            )
        )
    }

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param route: The destination the app needs to navigate to.
     */
    override fun navigateTopLevel(route: String) {
        val options = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(0) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
        navigate(NavigationState.NavigateToRoute(route, options))
    }

    @VisibleForTesting
    fun navigate(state: NavigationState) {
        navigationState.value = state
    }
}