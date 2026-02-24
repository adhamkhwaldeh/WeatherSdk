package com.adham.weatherSdk.orm

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.adham.weatherSdk.data.local.daos.AddressDao
import com.adham.weatherSdk.data.local.database.WeatherDatabase
import com.adham.weatherSdk.data.local.entities.AddressEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class AddressDaoTest {
    private lateinit var addressDao: AddressDao
    private lateinit var db: WeatherDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db =
            Room
                .inMemoryDatabaseBuilder(context, WeatherDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        addressDao = db.addressDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `drop address table`() {
        val currentAddresses = addressDao.loadAllData()
        addressDao.delete(currentAddresses)

        val allAddresses = addressDao.loadAllData()
        assertThat(allAddresses).isEmpty()
    }

    @Test
    fun `loadAllData empty table`() {
        val allAddresses = addressDao.loadAllData()
        assertThat(allAddresses).isEmpty()
    }

    @Test
    fun `loadAllData multiple records`() {
        val currentAddresses = addressDao.loadAllData()
        addressDao.delete(currentAddresses)

        val address1 = AddressEntity(id = 1, name = "London")
        val address2 = AddressEntity(id = 2, name = "New York")
        addressDao.insert(listOf(address1, address2))

        val allAddresses = addressDao.loadAllData()
        assertThat(allAddresses).hasSize(2)
//        assertThat(allAddresses).containsExactly(address1, address2)
    }

    @Test
    fun `getAddressById existing id`() {
        val address = AddressEntity(id = 1, name = "London")
        addressDao.insert(address)

        val found = addressDao.getAddressById(1)
        assertThat(found?.name).isEqualTo("London")
    }

    @Test
    fun `getAddressById non existent id`() {
        val found = addressDao.getAddressById(99)
        assertThat(found).isNull()
    }

    @Test
    fun `loadAllDataFlow initial emission`() =
        runBlocking {
            val address = AddressEntity(id = 1, name = "London")
            addressDao.insert(address)

            val allAddresses = addressDao.loadAllDataFlow().first()
            assertThat(allAddresses).hasSize(1)
            assertThat(allAddresses[0].name).isEqualTo("London")
        }

    @Test
    fun `findByName exact match`() =
        runBlocking {
            val address = AddressEntity(id = 1, name = "London")
            addressDao.insert(address)

            val found = addressDao.findByName("London")
            assertThat(found?.id).isEqualTo(1)
        }

    @Test
    fun `findByName no match`() =
        runBlocking {
            val found = addressDao.findByName("Unknown")
            assertThat(found).isNull()
        }

    @Test
    fun `findByName limit constraint`() =
        runBlocking {
            val address1 = AddressEntity(id = 1, name = "London")
            val address2 = AddressEntity(id = 2, name = "London")
            addressDao.insert(listOf(address1, address2))

            val found = addressDao.findByName("London")
            // Should return one of them due to LIMIT 1
            assertThat(found).isNotNull()
        }
}
