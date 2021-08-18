package com.sergiolopez.mobileweather.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainResponse(
    @Json(name = "temp")
    val temp: Double = 0.0,
    @Json(name = "feels_like")
    val feels_like: Double = 0.0,
    @Json(name = "temp_min")
    val temp_min: Double = 0.0,
    @Json(name = "temp_max")
    val temp_max: Double = 0.0,
    @Json(name = "pressure")
    val pressure: Double = 0.0,
    @Json(name = "humidity")
    val humidity: Double = 0.0
)