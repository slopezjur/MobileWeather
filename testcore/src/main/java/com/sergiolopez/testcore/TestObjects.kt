package com.sergiolopez.testcore

import com.sergiolopez.domain.model.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

fun buildOpenWeahter(): OpenWeather {
    return OpenWeather(
        Coordinates(0F, 0F),
        Weather(0, "", "", ""),
        Main(Random.nextInt(-50, 50), 0, 0, 0, 0, 0),
        Wind(0.0),
        formattedDate(),
        "Madrid, Spain"
    )
}

private fun formattedDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = Date()

    val formatter = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss.SSS",
        Locale("es", "ES")
    )

    return formatter.format(calendar.time)
}