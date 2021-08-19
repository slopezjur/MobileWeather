package com.sergiolopez.mobileweather.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.domain.model.Resource
import com.sergiolopez.domain.repository.MobileWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MobileWeatherViewModel @Inject constructor(
    private val repository: MobileWeatherRepository
) : ViewModel() {

    private val _spinner = MutableStateFlow(false)
    val spinner: StateFlow<Boolean> get() = _spinner

    val openWeatherLiveData = MutableLiveData<OpenWeather>()

    fun refreshOpenWeather(coordinates: Coordinates) {
        viewModelScope.launch {
            repository.getWeather(coordinates).collect {
                when (it) {
                    is Resource.Loading -> {
                        _spinner.value = true
                    }
                    is Resource.Success -> {
                        // Simulate server delay
                        delay(2000)
                        it.data.let { openWeather -> openWeatherLiveData.postValue(openWeather) }
                        _spinner.value = false
                    }
                    is Resource.Failure -> {
                        // TODO : Show error screen
                        _spinner.value = false
                    }
                }
            }
        }
    }
}
