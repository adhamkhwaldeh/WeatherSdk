package com.adham.gini.weatherginisdk.networking

import android.app.Application
import com.adham.gini.weatherginisdk.R
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherModel
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse
import com.adham.gini.weatherginisdk.data.dtos.HourlyForecastModel
import com.adham.gini.weatherginisdk.data.dtos.WeatherModel
import com.adham.gini.weatherginisdk.helpers.AssetsHelper
import com.adham.gini.weatherginisdk.helpers.AssetsHelper.readFromAssets
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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