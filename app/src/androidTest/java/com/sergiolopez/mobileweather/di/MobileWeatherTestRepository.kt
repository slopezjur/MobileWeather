package com.sergiolopez.mobileweather.di

import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.Resource
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.testcore.buildOpenWeahter
import kotlinx.coroutines.flow.flowOf

class MobileWeatherTestRepository : MobileWeatherRepository {

    override fun getWeather(coordinates: Coordinates) = flowOf(Resource.Success(buildOpenWeahter()))
}