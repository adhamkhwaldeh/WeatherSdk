package com.adham.weatherSdk.data.repositories

import android.content.Context
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.models.PlaceModel
import com.adham.weatherSdk.domain.repositories.PlacesRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume

internal class PlacesRepositoryImpl(
    context: Context,
) : PlacesRepository {
    private val client = Places.createClient(context)

    override suspend fun search(query: String): Result<List<PlaceModel>> =
        suspendCancellableCoroutine { cont ->

            val request = FindAutocompletePredictionsRequest.builder().setQuery(query).build()

            client
                .findAutocompletePredictions(request)
                .addOnSuccessListener {
                    cont.resume(
                        Result.success(
                            it.autocompletePredictions.map { prediction ->
                                PlaceModel(
                                    placeId = prediction.placeId,
                                    name = prediction.getFullText(null).toString(),
                                )
                            },
                        ),
                    )
                }.addOnFailureListener { cont.resume(Result.failure(it)) }
        }

    @Suppress("TooGenericExceptionThrown")
    override suspend fun loadPlaceDetails(placeId: String): Result<GeoByNameModel> {
        val fields =
            listOf(
                Place.Field.LOCATION,
                Place.Field.SHORT_FORMATTED_ADDRESS,
                Place.Field.DISPLAY_NAME,
            )

        val request = FetchPlaceRequest.builder(placeId, fields).build()

        return runCatching {
            val res = client.fetchPlace(request).await()
            if (res.place.location != null && res.place.displayName != null) {
                return Result.success(
                    GeoByNameModel(
                        name = res.place.displayName,
                        lat = res.place.location.latitude,
                        lon = res.place.location.longitude,
                    ),
                )
            }
            throw Exception("no data")
        }
    }
}
