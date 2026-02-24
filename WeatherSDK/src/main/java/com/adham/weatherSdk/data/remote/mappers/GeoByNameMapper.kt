package com.adham.weatherSdk.data.remote.mappers

import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.GeoByNameModel
import kotlin.text.trim

fun GeoByNameModel.toAddressModel(): AddressModel =
    AddressModel(
        name = name.trim(),
        lat = lat.toString(),
        lon = lon.toString(),
    )
