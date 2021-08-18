package com.sergiolopez.data.datasources

import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.domain.model.Resource

interface RemoteDataSource {
    suspend fun getWeather(coordinates: Coordinates): Resource<OpenWeather>
}