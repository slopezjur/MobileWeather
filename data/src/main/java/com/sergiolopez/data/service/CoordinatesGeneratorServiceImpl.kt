package com.sergiolopez.data.service

import com.sergiolopez.domain.service.CoordinatesGeneratorService
import kotlin.random.Random

class CoordinatesGeneratorServiceImpl : CoordinatesGeneratorService {

    override fun randomLatitude(): Float {
        return Random.nextDouble(-90.0, 90.0).toFloat()
    }

    override fun randomLongitude(): Float {
        return Random.nextDouble(-180.0, 180.0).toFloat()
    }
}