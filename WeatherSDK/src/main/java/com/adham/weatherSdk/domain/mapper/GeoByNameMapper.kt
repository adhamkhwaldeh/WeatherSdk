package com.adham.weatherSdk.domain.mapper

import com.adham.weatherSdk.data.remote.dtos.weatherMap.GeoByNameDto
import com.adham.weatherSdk.domain.models.GeoByNameModel

fun GeoByNameDto.toModel(): GeoByNameModel =
    GeoByNameModel(
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        country = this.country,
        state = this.state,
    )
