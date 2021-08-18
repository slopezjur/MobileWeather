package com.sergiolopez.mobileweather.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sergiolopez.domain.model.Coordinates
import com.sergiolopez.domain.model.OpenWeather
import com.sergiolopez.mobileweather.R
import com.sergiolopez.mobileweather.databinding.FragmentMobileWeatherBinding
import com.sergiolopez.mobileweather.utils.CoordinatesGenerator
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
        fragmentBinding.run {
            viewModel.spinner.onEach {
                progressIndicator.visible = it
                weatherGroup.visible = !it
            }.launchIn(lifecycleScope)

            viewModel.refreshOpenWeather(
                Coordinates(
                    CoordinatesGenerator().randomLatitude(),
                    CoordinatesGenerator().randomLongitude()
                )
            )
            viewModel.openWeatherLiveData.observe(viewLifecycleOwner, { openWeather ->
                setOpenWeatherInfo(openWeather)
            })
        }
    }

    private fun setOpenWeatherInfo(openWeather: OpenWeather) {
        fragmentBinding.layoutWeatherBasic.weatherTime.text = openWeather.datetime
        fragmentBinding.layoutWeatherBasic.weatherTemperature.text =
            openWeather.main.temp.toString()
        fragmentBinding.layoutWeatherBasic.weatherCityName.text = openWeather.name
    }
}