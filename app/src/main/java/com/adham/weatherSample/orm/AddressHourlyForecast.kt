package com.adham.weatherSample.orm

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Keep
@Entity
class AddressHourlyForecast() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "addressName")
    var addressName:String =""

    @ColumnInfo(name = "forecastDate")
    var forecastDate:String =""

    @ColumnInfo(name = "temp")
    var temp: Double = 0.0

    @ColumnInfo(name = "timestamp_local")
    var timestamp_local: String =""

    @ColumnInfo(name = "description")
    var description: String = ""

    @Ignore
//    @JvmOverloads
    constructor(
        id: Int = 0,
        addressName: String = "",
        forecastDate:String = "",
        temp: Double = 0.0,
        timestamp_local: String ="",
        description: String = ""
    ) : this() {
        this.id = id
        this.addressName = addressName

        this.forecastDate = forecastDate
        this.temp = temp
        this.timestamp_local = timestamp_local
        this.description = description
    }

}