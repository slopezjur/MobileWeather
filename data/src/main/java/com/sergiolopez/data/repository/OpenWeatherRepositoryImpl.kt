package com.sergiolopez.data.repository

import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.Resource
import com.sergiolopez.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.flow

class OpenWeatherRepositoryImpl constructor(
    private val remoteDataSource: RemoteDataSource,
) : OpenWeatherRepository {

    override fun getWeather(coordinates: Coordinates) = flow {
        emit(Resource.Loading)
        val openWeather = remoteDataSource.getWeather(coordinates)
        emit(openWeather)
    }
}