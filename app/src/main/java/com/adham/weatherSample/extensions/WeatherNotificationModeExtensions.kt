package com.adham.weatherSample.extensions

import androidx.annotation.StringRes
import com.adham.weatherSample.R
import com.adham.weatherSample.domain.models.WeatherNotificationMode
import com.adham.weatherSample.helpers.AppConstantsHelper

val WeatherNotificationMode.interval: Long
    get() {
        return when (this) {
            WeatherNotificationMode.MINUTES -> AppConstantsHelper.FIFTEEN_MINUTES
            WeatherNotificationMode.HOURLY -> AppConstantsHelper.AN_HOUR
            WeatherNotificationMode.DAILY -> AppConstantsHelper.A_DAY
            WeatherNotificationMode.NEVER -> AppConstantsHelper.ZERO
        }
    }

@get:StringRes
val WeatherNotificationMode.res: Int
    get() {
        return when (this) {
            WeatherNotificationMode.MINUTES -> R.string.minutes
            WeatherNotificationMode.HOURLY -> R.string.hourly
            WeatherNotificationMode.DAILY -> R.string.daily
            WeatherNotificationMode.NEVER -> R.string.never
        }
    }
