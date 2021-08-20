package com.sergiolopez.domain.service

interface CoordinatesGeneratorService {
    fun randomLatitude(): Float
    fun randomLongitude(): Float
}