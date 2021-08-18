package com.sergiolopez.mobileweather.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("es", "ES")

fun Long.timestampToDateTime(): String {

    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val formatter = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss",
        locale
    )

    return formatter.format(calendar.time)
}