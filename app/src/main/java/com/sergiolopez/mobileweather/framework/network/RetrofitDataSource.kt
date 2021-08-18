package com.sergiolopez.mobileweather.framework.network

import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.FailException
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.domain.model.Resource
import com.sergiolopez.mobileweather.BuildConfig
import com.sergiolopez.mobileweather.framework.mappers.toDomain

class RetrofitDataSource(
    private val openWeatherApi: OpenWeatherApi
) : RemoteDataSource {

    override suspend fun getWeather(coordinates: Coordinates): Resource<OpenWeather> {
        if (NetworkValidator.isNetworkConnected) {
            return openWeatherApi.getWeather(
                coordinates.latitude,
                coordinates.longitude,
                BuildConfig.API_KEY
            ).run {
                if (isSuccessful) {
                    body()?.let { body -> Resource.Success(body.toDomain()) } ?: Resource.Failure(
                        FailException.EmptyBody
                    )
                } else {
                    Resource.Failure(FailException.BadRequest)
                }
            }
        } else {
            return Resource.Failure(FailException.NoConnectionAvailable)
        }
    }
}