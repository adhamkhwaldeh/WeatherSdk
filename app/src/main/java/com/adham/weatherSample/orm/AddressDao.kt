package com.adham.weatherSample.orm

import androidx.room.Dao
import androidx.room.Query
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao : BaseDao<Address> {
    @Query("select * from address")
    fun loadAllData(): List<Address>

    @Query("SELECT * FROM address ORDER BY id DESC")
    fun getAddressesPaging(): PagingSource<Int, Address>

    @Query("SELECT * FROM address WHERE id = :addressId")
    fun getAddressById(addressId: Int): Address?

    @Query("select * from address")
    fun loadAllDataFlow(): Flow<List<Address>>

    @Query("SELECT * FROM address WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): Address?
}
