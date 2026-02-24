package com.adham.weatherSample.presentation.ui.navigations

import com.adham.weatherSdk.domain.models.AddressModel

/**
 * Navigation item
 *
 * @property route
 * @constructor Create empty Navigation item
 */
sealed class NavigationItem(
    val route: String,
) {
    object City : NavigationItem(ScreenEnum.City.name)

    object Forecast : NavigationItem(ScreenEnum.Forecast.name + "/{" + CITY_TAG + "}")

    object WeatherMapForecast :
        NavigationItem(
            ScreenEnum.WeatherMapForecast.name + "/{" +
                CITY_TAG +
                "}" +
                "/{" + LAT_TAG + "}" + "/{" + LON_TAG + "}",
        )

    companion object {
        const val CITY_TAG = "City"

        const val LAT_TAG = "Lat"
        const val LON_TAG = "Lon"

        fun forecastRouteWithParams(city: String): String = ScreenEnum.Forecast.name + "/$city"

        fun weatherMapForecastRouteWithParams(address: AddressModel): String =
            ScreenEnum.WeatherMapForecast.name + "/${address.name}" + "/${address.lat}" + "/${address.lon}"
    }
}
