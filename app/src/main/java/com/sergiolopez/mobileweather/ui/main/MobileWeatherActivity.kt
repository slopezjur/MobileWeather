package com.sergiolopez.mobileweather.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sergiolopez.mobileweather.databinding.ActivityMobileWeatherBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MobileWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMobileWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}