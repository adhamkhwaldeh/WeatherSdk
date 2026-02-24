package com.adham.weatherSdk.data.remote.networking

import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Weather service
 *
 * @constructor Create empty Weather service
 */
interface WeatherServiceApi : BaseWeatherServiceApi {
    /**
     * Current
     *
     * @param city
     * @param key
     * @return
     */
    @GET("v2.0/current")
    override suspend fun current(
        @Query("city") city: String,
        @Query("key") key: String,
    ): CurrentWeatherResponse

    /**
     * Forecast
     *
     * @param city
     * @param hours
     * @param key
     * @return
     */
    @GET("v2.0/forecast/hourly")
    override suspend fun forecast(
        @Query("city") city: String,
        @Query("hours") hours: Int,
        @Query("key") key: String,
    ): ForecastResponse
}
