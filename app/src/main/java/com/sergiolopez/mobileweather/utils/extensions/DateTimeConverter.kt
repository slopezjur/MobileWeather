package com.sergiolopez.mobileweather.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("es", "ES")

fun Long.timestampToDateTime(): String {

    val formatter = SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss z",
        locale
    )

    val date = Date(this * 1000)

    return formatter.format(date.time)
}