package com.adham.weatherSdk.data.local.mappers

import com.adham.weatherSdk.data.local.entities.AddressEntity
import com.adham.weatherSdk.domain.models.AddressModel

fun AddressModel.toEntity(): AddressEntity =
    AddressEntity(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        isDefault = this.isDefault,
    )

fun AddressEntity.toModel(): AddressModel =
    AddressModel(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        isDefault = this.isDefault,
    )
