package com.adham.weatherSample.presentation.states

import com.adham.weatherSdk.domain.models.AddressModel

data class AddressState(
    val savedAddresses: List<AddressModel> = listOf(),
    val selectedAddress: AddressModel? = null,
    val isNavigateToForecast: Boolean = true,
)
