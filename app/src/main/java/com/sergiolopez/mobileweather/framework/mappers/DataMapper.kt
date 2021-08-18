package com.sergiolopez.mobileweather.framework.mappers

import com.sergiolopez.domain.model.*
import com.sergiolopez.mobileweather.framework.network.*
import com.sergiolopez.mobileweather.utils.extensions.kelvinToCelsius
import com.sergiolopez.mobileweather.utils.extensions.timestampToDateTime

fun OpenWeatherResponse.toDomain() = OpenWeather(
    coordinates = coordinatesResponse.toDomain(),
    weather = weatherResponse.firstOrNull()?.toDomain() ?: Weather(0, "", "", ""),
    main = mainResponse.toDomain(),
    wind = windResposne.toDomain(),
    datetime = datetime.timestampToDateTime(),
    name = if (name.isEmpty()) CoordinatesMapper().randomLatitude(
        coordinatesResponse.latitude, coordinatesResponse.longitude
    ) else name
)

fun CoordinatesResponse.toDomain() = Coordinates(latitude, longitude)

fun WeatherResponse.toDomain() = Weather(id, main, description, icon)

fun MainResponse.toDomain() = Main(
    temp.kelvinToCelsius(),
    feels_like.kelvinToCelsius(),
    temp_min.kelvinToCelsius(),
    temp_max.kelvinToCelsius(),
    pressure.kelvinToCelsius(),
    humidity.kelvinToCelsius()
)

fun WindResposne.toDomain() = Wind(speed)
