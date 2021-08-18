package com.sergiolopez.mobileweather.utils.extensions

fun Double.kelvinToCelsius(): Int {
    return (this - 273.15).toInt()
}