package com.adham.weatherSample.ui.navigations

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

    companion object {
        const val CITY_TAG = "City"

        fun forecastRouteWithParams(city: String): String = ScreenEnum.Forecast.name + "/$city"
    }
}
