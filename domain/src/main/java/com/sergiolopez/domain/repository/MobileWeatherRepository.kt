package com.sergiolopez.domain.repository

import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface MobileWeatherRepository {
    fun getWeather(coordinates: Coordinates): Flow<Resource<OpenWeather>>
}
