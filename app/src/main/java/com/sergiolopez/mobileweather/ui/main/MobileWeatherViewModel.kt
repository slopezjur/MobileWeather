package com.sergiolopez.mobileweather.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiolopez.domain.model.*
import com.sergiolopez.domain.repository.MobileWeatherRepository
import com.sergiolopez.domain.service.CoordinatesGeneratorService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileWeatherViewModel @Inject constructor(
    private val repository: MobileWeatherRepository,
    private val coordinatesGeneratorService: CoordinatesGeneratorService
) : ViewModel() {

    private val initialFailureState = FailureState(FailureStateType.NONE)

    private val _spinner = MutableStateFlow(false)
    val spinner: StateFlow<Boolean> get() = _spinner

    private val _failureState = MutableStateFlow(initialFailureState)
    val failureStateType: StateFlow<FailureState> get() = _failureState

    val openWeatherLiveData = MutableLiveData<OpenWeather>()

    fun refreshOpenWeather() {
        viewModelScope.launch {
            repository.getWeather(
                Coordinates(
                    coordinatesGeneratorService.randomLatitude(),
                    coordinatesGeneratorService.randomLongitude()
                )
            ).collect {
                when (it) {
                    is Resource.Loading -> {
                        _spinner.value = true
                        _failureState.value = initialFailureState
                    }
                    is Resource.Success -> {
                        it.data.let { openWeather -> openWeatherLiveData.postValue(openWeather) }
                        // Simulate server delay
                        delay(2000)
                        _spinner.value = false
                    }
                    is Resource.Failure -> {
                        manageFailureState(it)
                        _spinner.value = false
                    }
                }
            }
        }
    }

    private fun manageFailureState(it: Resource.Failure) {
        when (it.exception) {
            is FailException.NoConnectionAvailable -> {
                _failureState.value =
                    FailureState(FailureStateType.NO_CONNECTION, it.exception.exceptionMessage)
            }
            is FailException.BadRequest -> {
                _failureState.value =
                    FailureState(FailureStateType.BAD_REQUEST, it.exception.exceptionMessage)
            }
            is FailException.EmptyBody -> {
                _failureState.value =
                    FailureState(FailureStateType.EMPTY_BODY, it.exception.exceptionMessage)
            }
        }
    }
}
