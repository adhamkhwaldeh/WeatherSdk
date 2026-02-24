package com.adham.weatherSdk.data.remote.networking

import com.adham.weatherSdk.data.remote.dtos.weatherMap.GeoByNameDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherMapGeoServiceApi {
    // http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    @GET("geo/1.0/direct")
    suspend fun getGeoByName(
        @Query("q") q: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") key: String,
    ): Set<GeoByNameDto>

    // http://api.openweathermap.org/geo/1.0/reverse?lat={lat}&lon={lon}&limit={limit}&appid={API key}
    @GET("geo/1.0/reverse")
    suspend fun getNameByGeo(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") key: String,
    ): Set<GeoByNameDto>
}
