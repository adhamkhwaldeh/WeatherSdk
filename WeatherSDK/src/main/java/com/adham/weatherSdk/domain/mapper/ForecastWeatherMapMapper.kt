package com.adham.weatherSdk.domain.mapper

import com.adham.weatherSdk.data.remote.dtos.weatherMap.CityWeatherMapDto
import com.adham.weatherSdk.data.remote.dtos.weatherMap.ForecastWeatherMapDto
import com.adham.weatherSdk.data.remote.dtos.weatherMap.ForecastWeatherMapResponse
import com.adham.weatherSdk.domain.models.CityWeatherMapModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel

fun ForecastWeatherMapResponse.toModel(): ForecastWeatherMapResponseModel =
    ForecastWeatherMapResponseModel(
        cod = this.cod,
        message = this.message,
        cnt = this.cnt,
        list = this.list.map { it.toModel() },
        city = this.city.toModel(),
    )

fun ForecastWeatherMapResponseModel.toDto(): ForecastWeatherMapResponse =
    ForecastWeatherMapResponse(
        cod = this.cod,
        message = this.message,
        cnt = this.cnt,
        list = this.list.map { it.toDto() },
        city = this.city.toDto(),
    )

fun ForecastWeatherMapDto.toModel(): ForecastWeatherMapModel =
    ForecastWeatherMapModel(
        dt = this.dt,
        main = this.main.toModel(),
        weather = this.weather.map { it.toModel() },
        dtTxt = this.dtTxt,
    )

fun ForecastWeatherMapModel.toDto(): ForecastWeatherMapDto =
    ForecastWeatherMapDto(
        dt = this.dt,
        main = this.main.toDto(),
        weather = this.weather.map { it.toDto() },
        dtTxt = this.dtTxt,
    )

fun CityWeatherMapDto.toModel(): CityWeatherMapModel =
    CityWeatherMapModel(
        id = this.id,
        name = this.name,
        country = this.country,
        timezone = this.timezone,
    )

fun CityWeatherMapModel.toDto(): CityWeatherMapDto =
    CityWeatherMapDto(
        id = this.id,
        name = this.name,
        country = this.country,
        timezone = this.timezone,
    )
