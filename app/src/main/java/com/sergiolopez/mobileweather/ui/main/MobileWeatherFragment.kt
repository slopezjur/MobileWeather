package com.sergiolopez.mobileweather.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sergiolopez.domain.model.FailureStateType
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.mobileweather.R
import com.sergiolopez.mobileweather.databinding.FragmentMobileWeatherBinding
import com.sergiolopez.mobileweather.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MobileWeatherFragment : Fragment(R.layout.fragment_mobile_weather) {

    private val viewModel: MobileWeatherViewModel by viewModels()

    private lateinit var fragmentBinding: FragmentMobileWeatherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBinding = FragmentMobileWeatherBinding.bind(view)
        fragmentBinding.refreshWeather.setOnClickListener {
            loadOpenWeather()
        }

        loadOpenWeather()
    }

    private fun loadOpenWeather() {
        managerLoadingState()
        manageDataState()
        manageFailureState()

        viewModel.refreshOpenWeather()
    }

    private fun managerLoadingState() {
        fragmentBinding.run {
            viewModel.spinner.onEach {
                progressIndicator.visible = it
                weatherGroup.visible = !it
                refreshWeather.visible = !it
            }.launchIn(lifecycleScope)
        }
    }

    private fun manageFailureState() {
        fragmentBinding.run {
            viewModel.failureStateType.onEach {
                weatherGroup.visible = false
                refreshWeather.visible = true

                when (it.failureStateType) {
                    FailureStateType.NO_CONNECTION -> {
                        failureNoConnectionGroup.visible = true
                    }
                    FailureStateType.BAD_REQUEST -> {
                        failureBadRequestGroup.visible = true
                        layoutFailureBadRequest.failureBadRequestText.text = it.failureMessage
                    }
                    FailureStateType.EMPTY_BODY -> {
                        failureNoConnectionGroup.visible = true
                    }
                    FailureStateType.NONE -> {
                        failuresGroup.visible = false
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }

    private fun manageDataState() {
        viewModel.openWeatherLiveData.observe(viewLifecycleOwner, { openWeather ->
            setOpenWeatherInfo(openWeather)
        })
    }

    private fun setOpenWeatherInfo(openWeather: OpenWeather) {
        fragmentBinding.apply {
            layoutWeatherInfo.weatherTime.text = openWeather.datetime
            layoutWeatherInfo.weatherTemperature.text = openWeather.main.temp.toString()
            layoutWeatherInfo.weatherTemperatureSymbol.text = getString(R.string.temperature_symbol)
            layoutWeatherInfo.weatherCityName.text = openWeather.name
        }
    }
}