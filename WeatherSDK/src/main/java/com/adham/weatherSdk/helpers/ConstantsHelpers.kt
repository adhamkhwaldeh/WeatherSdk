package com.adham.weatherSdk.helpers

/**
 * Constants helpers
 *
 * @constructor Create empty Constants helpers
 */
object ConstantsHelpers {
    const val BASE_URL = "http://api.weatherbit.io/"

    const val WEATHER_MAP_BASE_URL = "https://api.openweathermap.org/"

    const val TEST_API_KEY = "0e3cc1c8040a4d4882b318e57c60581e"

    const val NETWORK_REQUEST_TIMEOUT: Long = 10

    const val SHARED_PREFS_UTIL_PREFIX_TAG = "mySharedD_"
    const val API_KEY_TAG = "apiKey"

    const val API_WEATHER_KEY_TAG = "API_WEATHER_KEY_TAG"

    const val KELVIN_TO_CELSIUS: Double = 273.15
}
