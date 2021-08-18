package com.sergiolopez.mobileweather.utils

import kotlin.random.Random

class CoordinatesGenerator {

    fun randomLatitude(): Float {
        return Random.nextDouble(-90.0, 90.0).toFloat()
    }

    fun randomLongitude(): Float {
        return Random.nextDouble(-180.0, 180.0).toFloat()
    }
}