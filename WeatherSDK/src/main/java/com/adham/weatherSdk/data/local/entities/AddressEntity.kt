package com.adham.weatherSdk.data.local.entities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "address")
class AddressEntity() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @ColumnInfo(name = "lat")
    var lat: String = ""

    @ColumnInfo(name = "lon")
    var lon: String = ""

    @ColumnInfo(name = "isDefault")
    @JvmField
    var isDefault: Boolean = false

    @Ignore
    constructor(
        id: Int = 0,
        name: String = "",
        lat: String = "",
        lon: String = "",
        isDefault: Boolean = false,
    ) : this() {
        this.id = id
        this.name = name

        this.lat = lat
        this.lon = lon

        this.isDefault = isDefault
    }

    override fun equals(other: Any?): Boolean {
        if (other is AddressEntity) {
            return other.id == id
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lon.hashCode()
        return result
    }
}
