package com.sergiolopez.mobileweather.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenWeatherResponse(
    @Json(name = "coord")
    val coordinatesResponse: CoordinatesResponse = CoordinatesResponse(),
    @Json(name = "weather")
    val weatherResponse: List<WeatherResponse> = listOf(WeatherResponse()),
    @Json(name = "main")
    val mainResponse: MainResponse = MainResponse(),
    @Json(name = "wind")
    val windResposne: WindResposne = WindResposne(),
    @Json(name = "dt")
    val datetime: Long = 1629349861,
    @Json(name = "name")
    val name: String = ""
)