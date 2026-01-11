package com.adham.weatherSample.orm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(`object`: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(`object`: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(`object`: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(`object`: List<T>)

    @Delete
    fun delete(`object`: T)

    @Delete
    fun delete(`object`: List<T>)
}