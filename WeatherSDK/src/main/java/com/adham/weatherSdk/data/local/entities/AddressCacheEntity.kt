package com.adham.weatherSdk.data.local.entities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Keep
@Entity(
    tableName = "addressCache",
    primaryKeys = ["name", "lat", "lon"],
)
class AddressCacheEntity() {
    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "lat")
    var lat: String = ""

    @ColumnInfo(name = "lon")
    var lon: String = ""

    var currentWeather: CurrentWeatherMapResponse? = null

    var forecastWeather: ForecastWeatherMapResponse? = null

    @Ignore
    constructor(
        name: String = "",
        lat: String = "",
        lon: String = "",
        currentWeather: CurrentWeatherMapResponse? = null,
        forecastWeather: ForecastWeatherMapResponse? = null,
    ) : this() {
        this.name = name

        this.lat = lat
        this.lon = lon

        this.currentWeather = currentWeather
        this.forecastWeather = forecastWeather
    }
}
