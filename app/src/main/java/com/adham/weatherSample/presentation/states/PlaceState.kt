package com.adham.weatherSample.presentation.states

import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.models.PlaceModel

data class PlaceState(
    val query: String = "",
    val results: List<PlaceModel> = listOf(),
    val selectedPlace: GeoByNameModel? = null,
    val isExpanded: Boolean = false,
)
