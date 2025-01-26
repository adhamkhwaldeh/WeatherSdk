package com.adham.gini.weatherginisdk.data.dtos

import com.squareup.moshi.JsonClass

/**
 * Current weather model
 *
 * @property temp
 * @property ts
 * @property weather
 * @constructor Create empty Current weather model
 */
@JsonClass(generateAdapter = true)
data class CurrentWeatherModel(
//    val app_temp: Double,
//    val aqi: Int,
//    val city_name: String,
//    val clouds: Int,
//    val country_code: String,
//    val datetime: String,
//    val dewpt: Double,
//    val dhi: Int,
//    val dni: Int,
//    val elev_angle: Double,
//    val ghi: Int,
//    val gust: Double,
//    val h_angle: Double,
//    val lat: Double,
//    val lon: Double,
//    val ob_time: String,
//    val pod: String,
//    val precip: Double,
//    val pres: Int,
//    val rh: Int,

//    val slp: Int,
//    val snow: Double,
//    val solar_rad: Double,
//    val sources: MutableList<String>,

//    val state_code: String,
//    val station: String,

//    val sunrise: String,
//    val sunset: String,
    val temp: Double,
//    val timezone: String,
    val ts: Long,
//    val uv: Int,
//    val vis: Double,
    val weather: WeatherModel,

//    val wind_cdir: String,
//    val wind_cdir_full: String,
//    val wind_dir: Int,
//    val wind_spd: Double

)
