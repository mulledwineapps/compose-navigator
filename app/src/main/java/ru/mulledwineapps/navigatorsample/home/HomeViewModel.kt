package ru.mulledwineapps.navigatorsample.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import ru.mulledwineapps.composenavigator.IRouteNavigator
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    routeNavigator: IRouteNavigator,
) : ViewModel(), IRouteNavigator by routeNavigator {

    val uiState: StateFlow<HomeUiState> = flowOf(
        HomeUiState.Success(data = listOf("One", "Two", "Three"))
    )
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS), HomeUiState.Loading)

}

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val throwable: Throwable) : HomeUiState
    data class Success(val data: List<String>) : HomeUiState
}

private const val STOP_TIMEOUT_MILLIS: Long = 5_000L
