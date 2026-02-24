package com.adham.weatherSdk.providers

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.adham.weatherSdk.BuildConfig
import com.adham.weatherSdk.data.local.daos.AddressCacheDao
import com.adham.weatherSdk.data.local.daos.AddressDao
import com.adham.weatherSdk.data.local.database.WeatherDatabase
import com.adham.weatherSdk.data.preferences.SharedPrefsManagerImpl
import com.adham.weatherSdk.data.remote.networking.BaseWeatherServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherMapGeoServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherMapServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherMockedServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherServiceApi
import com.adham.weatherSdk.data.repositories.PlacesRepositoryImpl
import com.adham.weatherSdk.data.repositories.WeatherLocalRepositoryImpl
import com.adham.weatherSdk.data.repositories.WeatherMapLocalRepositoryImpl
import com.adham.weatherSdk.data.repositories.WeatherMapRepositoryImpl
import com.adham.weatherSdk.data.repositories.WeatherRepositoryImpl
import com.adham.weatherSdk.domain.preferences.SharedPrefsManager
import com.adham.weatherSdk.domain.repositories.PlacesRepository
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.adham.weatherSdk.helpers.ConstantsHelpers
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("TooManyFunctions")
internal object DataProvider {
    @Volatile
    private var sharedPrefsManager: SharedPrefsManager? = null

    @Volatile
    private var retrofit: Retrofit? = null

    @Volatile
    private var weatherServiceApi: WeatherServiceApi? = null

    @Volatile
    private var weatherMockedServiceApi: BaseWeatherServiceApi? = null

    @Volatile
    private var weatherLocalRepository: WeatherLocalRepository? = null

    @Volatile
    private var weatherRepository: WeatherRepository? = null

    @Volatile
    private var retrofitWeatherMap: Retrofit? = null

    @Volatile
    private var weatherMapServiceApi: WeatherMapServiceApi? = null

    @Volatile
    private var weatherMapGeoServiceApi: WeatherMapGeoServiceApi? = null

    @Volatile
    private var weatherMapLocalRepository: WeatherMapLocalRepository? = null

    @Volatile
    private var weatherMapRepository: WeatherMapRepository? = null

    @Volatile
    private var database: WeatherDatabase? = null

    @Volatile
    private var addressDao: AddressDao? = null

    @Volatile
    private var addressCacheDao: AddressCacheDao? = null

    @Volatile
    private var placeRepository: PlacesRepository? = null

    // #region  Weather
    fun provideSharedPrefsManager(context: Context): SharedPrefsManager {
        synchronized(this) {
            if (sharedPrefsManager == null) {
                val masterKey =
                    MasterKey
                        .Builder(context)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build()

                val sharedPreferences =
                    EncryptedSharedPreferences.create(
                        context,
                        ConstantsHelpers.SHARED_PREFS_UTIL_PREFIX_TAG + "secure_prefs",
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
                    )

                sharedPrefsManager = SharedPrefsManagerImpl(sharedPreferences)
            }
        }
        return sharedPrefsManager!!
    }

    fun provideRetrofit(): Retrofit {
        synchronized(this) {
            if (retrofit == null) {
                val logger =
                    HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                    }

                val retrofitBuilder = Retrofit.Builder()
                val moshi =
                    Moshi
                        .Builder() // adapter
                        .add(KotlinJsonAdapterFactory())
                        .build()
                retrofitBuilder
                    .apply {
//            baseUrl(localRepository.getBaseUrl())
                        baseUrl(ConstantsHelpers.BASE_URL)
                        addConverterFactory(MoshiConverterFactory.create(moshi))
                    }.also {
                        val okHttpClientBuilder = OkHttpClient.Builder()

                        okHttpClientBuilder.writeTimeout(
                            ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                            TimeUnit.SECONDS,
                        )
                        okHttpClientBuilder.readTimeout(
                            ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                            TimeUnit.SECONDS,
                        )
                        okHttpClientBuilder.connectTimeout(
                            ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                            TimeUnit.SECONDS,
                        )

                        okHttpClientBuilder.addInterceptor(logger)
                        it.client(okHttpClientBuilder.build())
                    }

                retrofit = retrofitBuilder.build()
            }
        }
        return retrofit!!
    }

    fun provideWeatherServiceApi(): WeatherServiceApi {
        synchronized(this) {
            if (weatherServiceApi == null) {
                weatherServiceApi = provideRetrofit().create(WeatherServiceApi::class.java)
            }
        }
        return weatherServiceApi!!
    }

    fun provideWeatherMockedServiceApi(context: Context): BaseWeatherServiceApi {
        synchronized(this) {
            if (weatherMockedServiceApi == null) {
                weatherMockedServiceApi = WeatherMockedServiceApi(context)
            }
        }
        return weatherMockedServiceApi!!
    }

    fun provideWeatherLocalRepository(context: Context): WeatherLocalRepository {
        synchronized(this) {
            if (weatherLocalRepository == null) {
                weatherLocalRepository =
                    WeatherLocalRepositoryImpl(provideSharedPrefsManager(context))
            }
        }
        return weatherLocalRepository!!
    }

    fun provideWeatherRepository(context: Context): WeatherRepository {
        synchronized(this) {
            if (weatherRepository == null) {
                weatherRepository =
                    WeatherRepositoryImpl(
                        provideWeatherServiceApi(),
                        provideWeatherMockedServiceApi(context),
                    )
            }
        }
        return weatherRepository!!
    }

    // #endregion

    // #region Weather Map

    fun provideRetrofitWeatherMap(): Retrofit {
        synchronized(this) {
            if (retrofitWeatherMap == null) {
                val logger =
                    HttpLoggingInterceptor().apply {
                        level =
                            if (BuildConfig.DEBUG) {
                                HttpLoggingInterceptor.Level.BODY
                            } else {
                                HttpLoggingInterceptor.Level.NONE
                            }
                    }

                val retrofitBuilder = Retrofit.Builder()
                val moshi =
                    Moshi
                        .Builder() // adapter
                        .add(KotlinJsonAdapterFactory())
                        .build()
                retrofitBuilder
                    .apply {
//            baseUrl(localRepository.getBaseUrl())
                        baseUrl(ConstantsHelpers.WEATHER_MAP_BASE_URL)
                        addConverterFactory(MoshiConverterFactory.create(moshi))
                    }.also {
                        val okHttpClientBuilder = OkHttpClient.Builder()

                        okHttpClientBuilder.writeTimeout(
                            ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                            TimeUnit.SECONDS,
                        )
                        okHttpClientBuilder.readTimeout(
                            ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                            TimeUnit.SECONDS,
                        )
                        okHttpClientBuilder.connectTimeout(
                            ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                            TimeUnit.SECONDS,
                        )

                        okHttpClientBuilder.addInterceptor(logger)
                        it.client(okHttpClientBuilder.build())
                    }

                retrofitWeatherMap = retrofitBuilder.build()
            }
        }
        return retrofitWeatherMap!!
    }

    fun provideWeatherMapServiceApi(): WeatherMapServiceApi {
        synchronized(this) {
            if (weatherMapServiceApi == null) {
                weatherMapServiceApi =
                    provideRetrofitWeatherMap().create(WeatherMapServiceApi::class.java)
            }
        }
        return weatherMapServiceApi!!
    }

    fun provideWeatherMapGeoServiceApi(): WeatherMapGeoServiceApi {
        synchronized(this) {
            if (weatherMapGeoServiceApi == null) {
                weatherMapGeoServiceApi =
                    provideRetrofitWeatherMap().create(WeatherMapGeoServiceApi::class.java)
            }
        }
        return weatherMapGeoServiceApi!!
    }

    fun provideWeatherMapLocalRepository(context: Context): WeatherMapLocalRepository {
        synchronized(this) {
            if (weatherMapLocalRepository == null) {
                weatherMapLocalRepository =
                    WeatherMapLocalRepositoryImpl(provideSharedPrefsManager(context))
            }
        }
        return weatherMapLocalRepository!!
    }

    fun provideWeatherMapRepository(context: Context): WeatherMapRepository {
        synchronized(this) {
            if (weatherMapRepository == null) {
                weatherMapRepository =
                    WeatherMapRepositoryImpl(
                        provideWeatherMapServiceApi(),
                        provideWeatherMapGeoServiceApi(),
                        addressCacheDao = provideAddressCacheDao(context),
                    )
            }
        }
        return weatherMapRepository!!
    }

    fun provideWeatherDatabase(context: Context): WeatherDatabase {
        synchronized(this) {
            if (database == null) {
                database = WeatherDatabase.getDatabase(context)
            }
        }
        return database!!
    }

    fun provideAddressDao(context: Context): AddressDao {
        synchronized(this) {
            if (addressDao == null) {
                addressDao = provideWeatherDatabase(context).addressDao()
            }
        }
        return addressDao!!
    }

    fun provideAddressCacheDao(context: Context): AddressCacheDao {
        synchronized(this) {
            if (addressCacheDao == null) {
                addressCacheDao = provideWeatherDatabase(context).addressCacheDao()
            }
        }
        return addressCacheDao!!
    }

    fun providePlacesRepository(context: Context): PlacesRepository {
        synchronized(this) {
            if (placeRepository == null) {
                placeRepository =
                    PlacesRepositoryImpl(
                        context = context,
                    )
            }
        }
        return placeRepository!!
    }

    // #endregion
}
