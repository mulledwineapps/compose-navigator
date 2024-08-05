package ru.mulledwineapps.navigatorsample

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.mulledwineapps.composenavigator.IRouteNavigator
import ru.mulledwineapps.composenavigator.RouteNavigator

@Module
@InstallIn(SingletonComponent::class)
internal class NavigatorModule {
    @Provides
    fun bindRouteNavigator(): IRouteNavigator = RouteNavigator()
}
