package com.adham.gini.weatherginisdk.data.dtos

data class CurrentWeatherResponse(
    val count: Int,
    val data: MutableList<CurrentWeatherModel>
)
