package com.adham.weatherSdk.data.local.database.converters

import androidx.room.TypeConverter
import com.adham.weatherSdk.data.remote.dtos.weatherMap.ForecastWeatherMapResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ForecastWeatherConverter {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private val adapter =
        moshi.adapter<ForecastWeatherMapResponse>(ForecastWeatherMapResponse::class.java)

    @TypeConverter
    fun fromForecastWeatherMapResponse(list: ForecastWeatherMapResponse?): String? = list?.let { adapter.toJson(it) }

    @TypeConverter
    fun toForecastWeatherMapResponse(json: String?): ForecastWeatherMapResponse? = json?.let { adapter.fromJson(it) }
}
