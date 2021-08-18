package com.sergiolopez.mobileweather

import android.app.Application
import com.sergiolopez.mobileweather.framework.network.NetworkMonitor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MobileWeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkMonitor(this).startNetworkCallback()
    }

    override fun onTerminate() {
        super.onTerminate()
        NetworkMonitor(this).stopNetworkCallback()
    }
}