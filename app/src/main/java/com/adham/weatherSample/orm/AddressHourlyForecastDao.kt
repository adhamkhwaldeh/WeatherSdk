package com.adham.weatherSample.orm

import androidx.room.Dao

@Dao
interface AddressHourlyForecastDao : BaseDao<AddressHourlyForecast> {
    // Transaction method
//    @Transaction
//    suspend fun addPointAndUpdateTrip(
//        newPoint: TripTrack,
//    ) {
//        val currentTrip = getTripById(newPoint.tripId) ?: return
//
//        val latestPoint = getLatestTripTracks(newPoint.tripId)
//        if (latestPoint != null) {
//            currentTrip.totalDistance += LocationDistanceHelper.distanceMeter(
//                latestPoint.latitude,
//                latestPoint.longitude,
//                newPoint.latitude,
//                newPoint.longitude
//            )
//            currentTrip.tripDuration += newPoint.timestamp - latestPoint.timestamp
//        }
//
//        currentTrip.totalPoints += 1
//
//        insert(newPoint)
//
//        updateTrip(currentTrip)
//
//    }
}
