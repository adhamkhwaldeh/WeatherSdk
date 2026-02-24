package com.adham.weatherSdk.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adham.weatherSdk.data.local.daos.AddressCacheDao
import com.adham.weatherSdk.data.local.daos.AddressDao
import com.adham.weatherSdk.data.local.database.converters.CurrentWeatherConverter
import com.adham.weatherSdk.data.local.database.converters.ForecastWeatherConverter
import com.adham.weatherSdk.data.local.entities.AddressCacheEntity
import com.adham.weatherSdk.data.local.entities.AddressEntity

@Database(
    entities = [AddressEntity::class, AddressCacheEntity::class],
    version = 6,
    exportSchema = false,
)
@TypeConverters(CurrentWeatherConverter::class, ForecastWeatherConverter::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao

    abstract fun addressCacheDao(): AddressCacheDao

    companion object {
        @Volatile
        private var instance: WeatherDatabase? = null

        fun getDatabase(context: Context): WeatherDatabase =
            instance ?: synchronized(this) {
                val instance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            WeatherDatabase::class.java,
                            "weather_database",
                        ).fallbackToDestructiveMigration(true)
                        .build()
                Companion.instance = instance
                instance
            }
    }
}
