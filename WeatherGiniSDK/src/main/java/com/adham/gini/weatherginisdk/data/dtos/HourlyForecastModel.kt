package com.adham.gini.weatherginisdk.data.dtos

/**
 * Hourly forecast model
 *
 * @property temp
 * @property timestamp_local
 * @property weather
 * @constructor Create empty Hourly forecast model
 */
data class HourlyForecastModel(
//    val app_temp: Double,
//    val clouds: Int,
//    val clouds_hi: Int,
//    val clouds_low: Int,
//    val clouds_mid: Int,
//    val datetime: String,
//    val dewpt: Double,
//    val dhi: Int,
//    val dni: Int,
//    val ghi: Int,
//    val ozone: Int,
//    val pod: String,
//    val pop: Int,
//    val precip: Double,
//    val pres: Int,
//    val rh: Int,
//    val slp: Int,
//    val snow: Double,
//    val snow_depth: Int,
//    val solar_rad: Double,
    val temp: Double,
    val timestamp_local: String,
//    val timestamp_utc: String,
//    val ts: Int,
//    val uv: Int,
//    val vis: Double,

    val weather: WeatherModel,
//    val wind_cdir: String,
//    val wind_cdir_full: String,
//    val wind_dir: Int,
//    val wind_gust_spd: Double,
//    val wind_spd: Double


)
