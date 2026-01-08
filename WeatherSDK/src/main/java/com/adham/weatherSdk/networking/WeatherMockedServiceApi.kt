package com.adham.weatherSdk.networking

import android.app.Application
import com.adham.weatherSdk.R
import com.adham.weatherSdk.data.dtos.CurrentWeatherModel
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.dtos.HourlyForecastModel
import com.adham.weatherSdk.data.dtos.WeatherModel
import com.adham.weatherSdk.helpers.AssetsHelper.readFromAssets
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class WeatherMockedServiceApi(val application: Application) {

    /**
     * Current
     *
     * @param city
     * @param key
     * @return
     */
    suspend fun current(
        city: String,
        key: String
    ): CurrentWeatherResponse {
        val json = application.resources.readFromAssets(R.raw.current)
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(
            CurrentWeatherResponse::class.java, CurrentWeatherModel::class.java,
            WeatherModel::class.java
        )
        val adapter: JsonAdapter<CurrentWeatherResponse> =
            moshi.adapter(type)
        return adapter.fromJson(json)!!
    }

    /**
     * Forecast
     *
     * @param city
     * @param hours
     * @param key
     * @return
     */
    suspend fun forecast(
        city: String,
        hours: Int,
        key: String
    ): ForecastResponse {
        val json = application.resources.readFromAssets(R.raw.hourly)
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(
            ForecastResponse::class.java, HourlyForecastModel::class.java,
            WeatherModel::class.java
        )
        val jsonAdapter: JsonAdapter<ForecastResponse> = moshi.adapter(type)
        return jsonAdapter.fromJson(json)!!
    }
}