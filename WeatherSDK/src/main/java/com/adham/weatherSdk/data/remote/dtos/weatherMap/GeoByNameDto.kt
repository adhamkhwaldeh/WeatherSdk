package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class GeoByNameDto(
    val name: String,
//    val local_names: LocalNamesModel,
//    @JsonProperty("local_names")
//    val localNames: LocalNames?,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?,
)
