package com.adham.weatherSample.presentation.states

import com.adham.weatherSdk.domain.models.GeoByNameModel

data class AddressGeoState(
    val query: String = "",
    val suggestions: List<GeoByNameModel> = listOf(),
    val isLoading: Boolean = false,
    val isExpanded: Boolean = false,
)
