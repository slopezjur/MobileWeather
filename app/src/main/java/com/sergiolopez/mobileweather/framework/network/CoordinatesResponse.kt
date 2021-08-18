package com.sergiolopez.mobileweather.framework.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordinatesResponse(
    @Json(name = "lat")
    val latitude: Float = 0.0F,
    @Json(name = "lon")
    val longitude: Float = -0.0F
)