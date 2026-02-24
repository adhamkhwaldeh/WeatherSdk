package com.adham.weatherSdk.domain.repositories

import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.models.PlaceModel

interface PlacesRepository {
    suspend fun search(query: String): Result<List<PlaceModel>>

    suspend fun loadPlaceDetails(placeId: String): Result<GeoByNameModel>
}
