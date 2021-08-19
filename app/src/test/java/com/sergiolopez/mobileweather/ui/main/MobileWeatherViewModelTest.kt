package com.sergiolopez.mobileweather.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sergiolopez.domain.model.*
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.testcore.BaseTest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
    lateinit var openWeatherObserver: Observer<OpenWeather>

    // Test MutableLiveData
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mobileWeatherViewModel = MobileWeatherViewModel(mobileWeatherRepository)
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsLoading_shouldShowSpinner() {
        runBlocking {
            val openWeahter = buildOpenWeahter()

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Loading
            )

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather(openWeahter.coordinates)

            assertEquals(mobileWeatherViewModel.spinner.value, true)
            verify(exactly = 0) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }

    @Test
    fun refreshOpenWeather_whenRefreshOpenWeatherIsSuccess_shouldShowData() {
        runBlocking {
            val openWeahter = buildOpenWeahter()

            coEvery {
                mobileWeatherRepository.getWeather(
                    openWeahter.coordinates
                )
            } returns flowOf(
                Resource.Success(openWeahter)
            )

            mobileWeatherViewModel.openWeatherLiveData.observeForever(openWeatherObserver)

            mobileWeatherViewModel.refreshOpenWeather(openWeahter.coordinates)

            assertEquals(mobileWeatherViewModel.spinner.value, false)
            verify(exactly = 1) {
                openWeatherObserver.onChanged(openWeahter)
            }
        }
    }

    private fun buildOpenWeahter() =
        OpenWeather(
            Coordinates(0F, 0F),
            Weather(0, "", "", ""),
            Main(0, 0, 0, 0, 0, 0),
            Wind(0.0),
            "",
            ""
        )
}