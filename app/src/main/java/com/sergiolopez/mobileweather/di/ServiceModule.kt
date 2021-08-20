package com.sergiolopez.mobileweather.di

import com.sergiolopez.domain.service.CoordinatesGeneratorService
import com.sergiolopez.data.service.CoordinatesGeneratorServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideCoordinatesGenerator(): CoordinatesGeneratorService =
        CoordinatesGeneratorServiceImpl()
}
