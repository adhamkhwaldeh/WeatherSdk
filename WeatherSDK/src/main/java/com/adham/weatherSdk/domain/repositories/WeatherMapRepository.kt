package com.adham.weatherSdk.domain.repositories

import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.flow.Flow

interface WeatherMapRepository {
    // https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    suspend fun current(
//        lat: String,
//        lon: String,
        address: AddressModel,
        key: String,
    ): Flow<BaseState<CurrentWeatherMapResponseModel>>
//    ): Result<CurrentWeatherMapResponse>

    // api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
    suspend fun forecast(
//        lat: String,
//        lon: String,
        address: AddressModel,
        key: String,
    ): Flow<BaseState<ForecastWeatherMapResponseModel>>
//    ): Result<ForecastWeatherMapResponse>

    // http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    suspend fun getGeoByName(
        q: String,
        limit: Int = 5,
        key: String,
    ): Result<Set<GeoByNameModel>>

    // http://api.openweathermap.org/geo/1.0/reverse?lat={lat}&lon={lon}&limit={limit}&appid={API key}
    suspend fun getNameByGeo(
        lat: String,
        lon: String,
        limit: Int = 5,
        key: String,
    ): Result<Set<GeoByNameModel>>
}
