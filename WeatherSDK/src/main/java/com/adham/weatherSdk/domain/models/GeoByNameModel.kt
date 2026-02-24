package com.adham.weatherSdk.domain.models

data class GeoByNameModel(
    val name: String,
//    val local_names: LocalNamesModel,
//    @JsonProperty("local_names")
//    val localNames: LocalNames?,
    val lat: Double,
    val lon: Double,
    val country: String = "",
    val state: String? = "",
)
