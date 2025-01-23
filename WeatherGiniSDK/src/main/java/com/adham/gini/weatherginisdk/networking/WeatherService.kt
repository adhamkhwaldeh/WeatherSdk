package com.adham.gini.weatherginisdk.networking

import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {

    @GET("v2.0/current")
    suspend fun current(
        @Query("city") city: String,
        @Query("key") key: String
    ): CurrentWeatherResponse

    @GET("v2.0/forecast/hourly")
    suspend fun forecast(
        @Query("city") city: String,
        @Query("hours") hours: Int,
        @Query("key") key: String
    ): ForecastResponse

}