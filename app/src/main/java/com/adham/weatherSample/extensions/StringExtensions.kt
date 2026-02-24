package com.adham.weatherSample.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun String.getDayOfWeek(): String =
    try {
        val date = LocalDate.parse(this)
        val formatter = DateTimeFormatter.ofPattern("EEEE")
        date.format(formatter)
    } catch (_: Exception) {
        ""
    }
