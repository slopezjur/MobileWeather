package com.sergiolopez.mobileweather.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sergiolopez.domain.model.*
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.domain.service.CoordinatesGeneratorService
import com.sergiolopez.testcore.BaseTest
import com.sergiolopez.testcore.buildOpenWeahter
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MobileWeatherViewModelTest : BaseTest() {

    lateinit var mobileWeatherViewModel: MobileWeatherViewModel

    @MockK
    lateinit var mobileWeatherRepository: MobileWeatherRepository

    @MockK
    lateinit var coordinatesGeneratorService: CoordinatesGeneratorService

    @MockK
    lateinit var openWeatherObserver: Observer<OpenWeather>

    // Test MutableLiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mobileWeatherViewModel =
            MobileWeatherViewModel(mobileWeatherRepository, coordinatesGeneratorService)
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsLoading_shouldShowSpinner() {
        runBlocking {
            val openWeahter = buildOpenWeahter()
            val failureState = FailureState(FailureStateType.NONE)

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Loading
            )

            every {
                coordinatesGeneratorService.randomLatitude()
            } returns openWeahter.coordinates.latitude

            every {
                coordinatesGeneratorService.randomLongitude()
            } returns openWeahter.coordinates.longitude

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather()

            assertEquals(mobileWeatherViewModel.spinner.value, true)
            assertEquals(mobileWeatherViewModel.failureStateType.value, failureState)
            verify(exactly = 0) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsSuccess_shouldShowData() {
        runBlocking {
            val openWeahter = buildOpenWeahter()
            val failureState = FailureState(FailureStateType.NONE)

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Success(openWeahter)
            )

            every {
                coordinatesGeneratorService.randomLatitude()
            } returns openWeahter.coordinates.latitude

            every {
                coordinatesGeneratorService.randomLongitude()
            } returns openWeahter.coordinates.longitude

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather()

            assertEquals(mobileWeatherViewModel.spinner.value, false)
            assertEquals(mobileWeatherViewModel.failureStateType.value, failureState)
            verify(exactly = 1) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsNoConnection_shouldShowNoData() {
        runBlocking {
            val openWeahter = buildOpenWeahter()
            val failException = FailException.NoConnectionAvailable
            val failureState = FailureState(FailureStateType.NO_CONNECTION)

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Failure(failException)
            )

            every {
                coordinatesGeneratorService.randomLatitude()
            } returns openWeahter.coordinates.latitude

            every {
                coordinatesGeneratorService.randomLongitude()
            } returns openWeahter.coordinates.longitude

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather()

            assertEquals(mobileWeatherViewModel.spinner.value, false)
            assertEquals(mobileWeatherViewModel.failureStateType.value, failureState)
            verify(exactly = 0) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsBadRequest_shouldShowNoData() {
        runBlocking {
            val openWeahter = buildOpenWeahter()
            val failureMessage = "Bad request"
            val failException = FailException.BadRequest(failureMessage)
            val failureState = FailureState(FailureStateType.BAD_REQUEST, failureMessage)

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Failure(failException)
            )

            every {
                coordinatesGeneratorService.randomLatitude()
            } returns openWeahter.coordinates.latitude

            every {
                coordinatesGeneratorService.randomLongitude()
            } returns openWeahter.coordinates.longitude

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather()

            assertEquals(mobileWeatherViewModel.spinner.value, false)
            assertEquals(mobileWeatherViewModel.failureStateType.value, failureState)
            verify(exactly = 0) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsEmptyBody_shouldShowNoData() {
        runBlocking {
            val openWeahter = buildOpenWeahter()
            val failureMessage = "Empty body"
            val failException = FailException.EmptyBody(failureMessage)
            val failureState = FailureState(FailureStateType.EMPTY_BODY, failureMessage)

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Failure(failException)
            )

            every {
                coordinatesGeneratorService.randomLatitude()
            } returns openWeahter.coordinates.latitude

            every {
                coordinatesGeneratorService.randomLongitude()
            } returns openWeahter.coordinates.longitude

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather()

            assertEquals(mobileWeatherViewModel.spinner.value, false)
            assertEquals(mobileWeatherViewModel.failureStateType.value, failureState)
            verify(exactly = 0) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }
}