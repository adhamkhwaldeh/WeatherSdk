package com.adham.weatherSample.orm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Address::class, AddressHourlyForecast::class], version = 2)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun addressDao(): AddressDao

    abstract fun addressHourlyForecastDao(): AddressHourlyForecastDao

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
