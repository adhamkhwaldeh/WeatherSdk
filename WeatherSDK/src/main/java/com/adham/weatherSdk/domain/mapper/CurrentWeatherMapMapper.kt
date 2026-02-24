package com.adham.weatherSdk.domain.mapper

import com.adham.weatherSdk.data.remote.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.remote.dtos.weatherMap.MainWeatherMapDto
import com.adham.weatherSdk.data.remote.dtos.weatherMap.WeatherMapDto
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.MainWeatherMapModel
import com.adham.weatherSdk.domain.models.WeatherMapModel

fun CurrentWeatherMapResponse.toModel(): CurrentWeatherMapResponseModel =
    CurrentWeatherMapResponseModel(
        weather = this.weather.map { it.toModel() },
        main = this.main.toModel(),
        sys = this.sys,
        timezone = this.timezone,
        id = this.id,
        name = this.name,
    )

fun CurrentWeatherMapResponseModel.toDto(): CurrentWeatherMapResponse =
    CurrentWeatherMapResponse(
        weather = this.weather.map { it.toDto() },
        main = this.main.toDto(),
        sys = this.sys,
        timezone = this.timezone,
        id = this.id,
        name = this.name,
    )

fun WeatherMapDto.toModel(): WeatherMapModel =
    WeatherMapModel(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon,
    )

fun WeatherMapModel.toDto(): WeatherMapDto =
    WeatherMapDto(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon,
    )

fun MainWeatherMapDto.toModel(): MainWeatherMapModel =
    MainWeatherMapModel(
        temp = this.temp,
        tempMin = this.tempMin,
        tempMax = this.tempMax,
        pressure = this.pressure,
        humidity = this.humidity,
    )

fun MainWeatherMapModel.toDto(): MainWeatherMapDto =
    MainWeatherMapDto(
        temp = this.temp,
        tempMin = this.tempMin,
        tempMax = this.tempMax,
        pressure = this.pressure,
        humidity = this.humidity,
    )
