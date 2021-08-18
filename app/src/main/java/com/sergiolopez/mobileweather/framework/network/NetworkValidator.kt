package com.sergiolopez.mobileweather.framework.network

import kotlin.properties.Delegates

object NetworkValidator {
    var isNetworkConnected: Boolean by Delegates.observable(false) { _, _, _ -> }
}