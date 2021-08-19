package com.sergiolopez.mobileweather.di

import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.repository.MobileWeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource
    ): MobileWeatherRepository =
        com.sergiolopez.data.repository.MobileWeatherRepositoryImpl(remoteDataSource)

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
