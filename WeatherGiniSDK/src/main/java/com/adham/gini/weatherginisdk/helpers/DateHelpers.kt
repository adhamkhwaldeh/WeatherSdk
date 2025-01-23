package com.adham.gini.weatherginisdk.helpers

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.Temporal
import java.time.temporal.TemporalField
import java.util.*

object DateHelpers {

    fun convertTimestampToLocalTime(timestamp: Long): String {
        // Create an Instant from the timestamp (in milliseconds)
        val instant = Instant.ofEpochMilli(timestamp)

        // Get the local timezone from the system
        val zoneId = ZoneId.systemDefault()

        // Convert the instant to the local time zone
        val localDateTime = ZonedDateTime.ofInstant(instant, zoneId)

        // Format the ZonedDateTime to a readable string
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return localDateTime.format(formatter)
    }

    fun getHoursOnlyFromStanderFormat(dateStr: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDateTime.parse(dateStr, formatter)
        return String.format("%02d", date.hour) + ":" + String.format("%02d", date.minute)
    }
}