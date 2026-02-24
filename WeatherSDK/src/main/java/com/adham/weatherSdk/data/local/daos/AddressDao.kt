package com.adham.weatherSdk.data.local.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.adham.weatherSdk.data.local.entities.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao : BaseDao<AddressEntity> {
    @Query("select * from address")
    fun loadAllData(): List<AddressEntity>

    @Query("SELECT * FROM address ORDER BY id DESC")
    fun getAddressesPaging(): PagingSource<Int, AddressEntity>

    @Query("SELECT * FROM address WHERE id = :addressId limit 1")
    fun getAddressById(addressId: Int): AddressEntity?

    @Query("SELECT * FROM address WHERE isDefault = 1 limit 1")
    suspend fun getDefaultAddress(): AddressEntity?

    @Query("SELECT * FROM address WHERE id != :addressId and isDefault = 1")
    suspend fun getOtherDefaultAddresses(addressId: Int): List<AddressEntity>

    @Query("select * from address")
    fun loadAllDataFlow(): Flow<List<AddressEntity>>

    @Query("SELECT * FROM address WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): AddressEntity?

    @Transaction
    suspend fun insertOrUpdateWithDefault(entity: AddressEntity): AddressEntity {
        val defaultAddresses = getOtherDefaultAddresses(entity.id)
        if (defaultAddresses.isEmpty()) {
            entity.isDefault = true
        }
        if (entity.isDefault) {
            defaultAddresses.forEach {
                it.isDefault = false
            }
            update(defaultAddresses)
        }

        // Insert here is enough as well defined replace Strategy
        if (entity.id > 0) {
            update(entity)
        } else {
            insert(entity)
        }
        return entity
    }
}
