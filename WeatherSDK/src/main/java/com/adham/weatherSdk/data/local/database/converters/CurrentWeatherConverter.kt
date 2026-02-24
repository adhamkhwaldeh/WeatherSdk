package com.adham.weatherSdk.data.local.database.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class CurrentWeatherConverter {
    private val moshi =
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val adapter =
        moshi.adapter<CurrentWeatherMapResponse>(CurrentWeatherMapResponse::class.java)

    @TypeConverter
    fun fromCurrentWeatherMapResponse(response: CurrentWeatherMapResponse?): String? =
        response?.let {
            adapter.toJson(it)
        }

    @TypeConverter
    fun toCurrentWeatherMapResponse(json: String?): CurrentWeatherMapResponse? =
        json?.let {
            adapter.fromJson(it)
        }
}
