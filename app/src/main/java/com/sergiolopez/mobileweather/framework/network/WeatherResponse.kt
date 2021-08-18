package com.sergiolopez.mobileweather.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "main")
    val main: String = "",
    @Json(name = "description")
    val description: String = "",
    @Json(name = "icon")
    val icon: String = ""
)