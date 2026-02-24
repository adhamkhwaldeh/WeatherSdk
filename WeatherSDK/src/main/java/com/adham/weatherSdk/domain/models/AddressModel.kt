package com.adham.weatherSdk.domain.models

data class AddressModel(
    val id: Int = 0,
    val name: String = "",
    val lat: String = "",
    val lon: String = "",
    val isDefault: Boolean = false,
)
