package com.adham.gini.weatherginisdk.ui.navigations

sealed class NavigationItem(val route: String) {

    object City : NavigationItem(ScreenEnum.City.name)

    object Forecast : NavigationItem(ScreenEnum.Forecast.name + "/{" + CityTag + "}")

    companion object {
        const val CityTag = "City"
        fun forecastRouteWithParams(city: String): String {
            return ScreenEnum.Forecast.name + "/$city"
        }
    }

}
