package com.sergiolopez.mobileweather.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindResposne(
    @Json(name = "speed")
    val speed: Double = 0.0
)