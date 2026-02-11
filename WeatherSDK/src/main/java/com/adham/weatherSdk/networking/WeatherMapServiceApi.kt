package com.adham.weatherSdk.networking

import com.adham.weatherSdk.data.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.dtos.weatherMap.ForecastWeatherMapResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherMapServiceApi {
    /**
     * Current
     *
     * @param lat
     * @param key
     * @return
     */
    @GET("data/2.5/weather")
    // https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    suspend fun current(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") key: String,
    ): CurrentWeatherMapResponse

    /**
     * Forecast
     *
     * @param lat
     * @param lon
     * @param key
     * @return
     */
    @GET("data/2.5/forecast")
    // api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
    suspend fun forecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") key: String,
    ): ForecastWeatherMapResponse
}
