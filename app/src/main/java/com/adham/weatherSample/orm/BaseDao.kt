package com.adham.weatherSample.orm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: List<T>)

    @Delete
    fun delete(entity: T)

    @Delete
    fun delete(entity: List<T>)
}
