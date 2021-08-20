package com.sergiolopez.mobileweather.di

import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.domain.model.Resource
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.testcore.buildOpenWeahter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MobileWeatherTestRepository(
    private var resource: Resource<OpenWeather>
) : MobileWeatherRepository {

    override fun getWeather(coordinates: Coordinates): Flow<Resource<OpenWeather>> {
        when (resource) {
            is Resource.Success -> {
                resource = Resource.Success(buildOpenWeahter())
            }
            is Resource.Failure -> {
                // Do nothing
            }
            is Resource.Loading -> {
                // Do nothing
            }
        }

        return flowOf(resource)
    }
}