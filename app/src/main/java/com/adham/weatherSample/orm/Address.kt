package com.adham.weatherSample.orm

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Keep
@Entity
class Address() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "name")
    var name: String = ""

    @Ignore
    constructor(
        id: Int = 0,
        name: String = "",
    ) : this() {
        this.id = id
        this.name = name
    }
}
