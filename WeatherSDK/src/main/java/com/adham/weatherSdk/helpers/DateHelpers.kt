package com.adham.weatherSdk.helpers

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Date helpers
 *
 * @constructor Create empty Date helpers
 */
object DateHelpers {

    /**
     * Convert timestamp to local time
     *
     * @param timestamp
     * @return
     */
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

    /**
     * Get hours only from stander format
     *
     * @param dateStr
     * @return
     */
    fun getHoursOnlyFromStanderFormat(dateStr: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val date = LocalDateTime.parse(dateStr, formatter)
        return String.format("%02d", date.hour) + ":" + String.format("%02d", date.minute)
    }
}