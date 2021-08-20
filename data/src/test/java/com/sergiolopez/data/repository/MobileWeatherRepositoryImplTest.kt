package com.sergiolopez.data.repository

import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.model.*
import com.sergiolopez.testcore.BaseTest
import com.sergiolopez.testcore.buildOpenWeahter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MobileWeatherRepositoryImplTest : BaseTest() {

    lateinit var mobileWeatherRepository: MobileWeatherRepositoryImpl

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        mobileWeatherRepository = MobileWeatherRepositoryImpl(remoteDataSource)
    }

    @Test
    fun getWeahter_whenGetWeatherAndSuccess_shouldGetData() {
        runBlocking {
            val openWeather = buildOpenWeahter()

            val openWeatherFlow = flow {
                emit(Resource.Loading)
                emit(Resource.Success(openWeather))
            }.toList()

            val resourceOpenWeahter = Resource.Success(openWeather)

            `when`(remoteDataSource.getWeather(openWeather.coordinates)).thenReturn(
                resourceOpenWeahter
            )

            val openWeatherFlowResult =
                mobileWeatherRepository.getWeather(openWeather.coordinates).toList()

            assertEquals(openWeatherFlowResult[0], openWeatherFlow[0])
            assertEquals(openWeatherFlowResult[1], openWeatherFlow[1])
        }
    }

    @Test
    fun getWeahter_whenGetWeatherAndFail_shouldNotGetData() {
        runBlocking {
            val openWeather = buildOpenWeahter()

            val openWeatherFlow = flow {
                emit(Resource.Loading)
                emit(Resource.Failure(FailException.EmptyBody))
            }.toList()

            val resourceOpenWeahter = Resource.Failure(FailException.EmptyBody)

            `when`(remoteDataSource.getWeather(openWeather.coordinates)).thenReturn(
                resourceOpenWeahter
            )

            val openWeatherFlowResult =
                mobileWeatherRepository.getWeather(openWeather.coordinates).toList()

            assertEquals(openWeatherFlowResult[0], openWeatherFlow[0])
            assertEquals(openWeatherFlowResult[1], openWeatherFlow[1])
        }
    }

    @Test
    fun getWeahter_whenGetWeatherAndBadRequest_shouldNotGetData() {
        runBlocking {
            val openWeather = buildOpenWeahter()

            val openWeatherFlow = flow {
                emit(Resource.Loading)
                emit(Resource.Failure(FailException.BadRequest))
            }.toList()

            val resourceOpenWeahter = Resource.Failure(FailException.BadRequest)

            `when`(remoteDataSource.getWeather(openWeather.coordinates)).thenReturn(
                resourceOpenWeahter
            )

            val openWeatherFlowResult =
                mobileWeatherRepository.getWeather(openWeather.coordinates).toList()

            assertEquals(openWeatherFlowResult[0], openWeatherFlow[0])
            assertEquals(openWeatherFlowResult[1], openWeatherFlow[1])
        }
    }

    @Test
    fun getWeahter_whenGetWeatherAndNoConnection_shouldNotGetData() {
        runBlocking {
            val openWeather = buildOpenWeahter()

            val openWeatherFlow = flow {
                emit(Resource.Loading)
                emit(Resource.Failure(FailException.NoConnectionAvailable))
            }.toList()

            val resourceOpenWeahter = Resource.Failure(FailException.NoConnectionAvailable)

            `when`(remoteDataSource.getWeather(openWeather.coordinates)).thenReturn(
                resourceOpenWeahter
            )

            val openWeatherFlowResult =
                mobileWeatherRepository.getWeather(openWeather.coordinates).toList()

            assertEquals(openWeatherFlowResult[0], openWeatherFlow[0])
            assertEquals(openWeatherFlowResult[1], openWeatherFlow[1])
        }
    }
}