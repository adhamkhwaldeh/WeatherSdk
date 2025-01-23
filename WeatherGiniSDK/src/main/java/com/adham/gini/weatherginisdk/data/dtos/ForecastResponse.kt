package com.adham.gini.weatherginisdk.data.dtos

data class ForecastResponse(
    val city_name: String,
    val country_code: String,
    val data: List<HourlyForecastModel>,
    val lat: String,
    val lon: String,
    val state_code: String,
    val timezone: String

)
