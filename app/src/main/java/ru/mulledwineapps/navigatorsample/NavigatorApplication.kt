package ru.mulledwineapps.navigatorsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * [Application] class for NavigatorApplication
 */
@HiltAndroidApp
class NavigatorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
