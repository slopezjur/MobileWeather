package com.sergiolopez.data.repository

import com.sergiolopez.data.datasources.RemoteDataSource
import com.sergiolopez.domain.model.*
import com.sergiolopez.testcore.BaseTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MobileWeatherRepositoryImplMockkTest : BaseTest() {

    lateinit var mobileWeatherRepository: MobileWeatherRepositoryImpl

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
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

            coEvery { remoteDataSource.getWeather(openWeather.coordinates) } returns resourceOpenWeahter

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

            coEvery { remoteDataSource.getWeather(openWeather.coordinates) } returns resourceOpenWeahter

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

            coEvery { remoteDataSource.getWeather(openWeather.coordinates) } returns resourceOpenWeahter

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

            coEvery { remoteDataSource.getWeather(openWeather.coordinates) } returns resourceOpenWeahter

            val openWeatherFlowResult =
                mobileWeatherRepository.getWeather(openWeather.coordinates).toList()

            assertEquals(openWeatherFlowResult[0], openWeatherFlow[0])
            assertEquals(openWeatherFlowResult[1], openWeatherFlow[1])
        }
    }

    fun buildOpenWeahter() =
        OpenWeather(
            Coordinates(0F, 0F),
            Weather(0, "", "", ""),
            Main(0, 0, 0, 0, 0, 0),
            Wind(0.0),
            "",
            ""
        )
}