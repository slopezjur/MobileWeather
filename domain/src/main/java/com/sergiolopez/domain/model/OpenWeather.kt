package com.sergiolopez.domain.model

data class OpenWeather(
    val coordinates: Coordinates,
    val weather: Weather,
    val main: Main,
    val wind: Wind,
    val datetime: String,
    val name: String
)