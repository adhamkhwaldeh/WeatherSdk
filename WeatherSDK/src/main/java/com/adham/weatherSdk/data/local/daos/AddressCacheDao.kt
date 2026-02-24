package com.adham.weatherSdk.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adham.weatherSdk.data.local.entities.AddressCacheEntity
import com.adham.weatherSdk.data.local.entities.AddressEntity
import com.adham.weatherSdk.data.remote.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.remote.dtos.weatherMap.ForecastWeatherMapResponse

@Dao
interface AddressCacheDao : BaseDao<AddressCacheEntity> {
    @Query("SELECT * FROM addressCache WHERE name = :addressName and lon = :lon and lat = :lat limit 1")
    fun getAddressCache(
        addressName: String,
        lon: String,
        lat: String,
    ): AddressCacheEntity?

    @Transaction
    suspend fun upsertCurrentWeather(
        address: AddressEntity,
        currentWeather: CurrentWeatherMapResponse,
    ) {
        val results = getAddressCache(address.name, address.lon, address.lat)
        if (results == null) {
            insert(
                AddressCacheEntity(
                    name = address.name,
                    lon = address.lon,
                    lat = address.lat,
                    currentWeather = currentWeather,
                ),
            )
        } else {
            results.currentWeather = currentWeather
            update(results)
        }
    }

    @Transaction
    suspend fun upsertForecastWeather(
        address: AddressEntity,
        forecastWeather: ForecastWeatherMapResponse,
    ) {
        val results = getAddressCache(address.name, address.lon, address.lat)
        if (results == null) {
            insert(
                AddressCacheEntity(
                    name = address.name,
                    lon = address.lon,
                    lat = address.lat,
                    forecastWeather = forecastWeather,
                ),
            )
        } else {
            results.forecastWeather = forecastWeather
            update(results)
        }
    }
}
