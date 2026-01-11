package com.adham.weatherSdk.providers

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.adham.weatherSdk.helpers.ConstantsHelpers
import com.adham.weatherSdk.localStorages.SharedPrefsManager
import com.adham.weatherSdk.localStorages.SharedPrefsManagerImpl
import com.adham.weatherSdk.networking.BaseWeatherServiceApi
import com.adham.weatherSdk.networking.WeatherMockedServiceApi
import com.adham.weatherSdk.networking.WeatherServiceApi
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.data.repositories.WeatherLocalRepositoryImpl
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.adham.weatherSdk.data.repositories.WeatherRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

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


    fun provideSharedPrefsManager(
        context: Context
    ): SharedPrefsManager {
        synchronized(this) {
            if (sharedPrefsManager == null) {
                val masterKey = MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

                val sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    SharedPrefsManagerImpl.SHARED_PREFS_UTIL_PREFIX + "secure_prefs",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                sharedPrefsManager = SharedPrefsManagerImpl(sharedPreferences)
            }
        }
        return sharedPrefsManager!!
    }

    fun provideRetrofit(): Retrofit {
        synchronized(this) {
            if (retrofit == null) {
                val logger = HttpLoggingInterceptor().apply {

                    level = HttpLoggingInterceptor.Level.BODY

//            if (BuildConfig.DEBUG)
//                HttpLoggingInterceptor.Level.BODY
//            else
//                HttpLoggingInterceptor.Level.NONE

                }

                val retrofitBuilder = Retrofit.Builder()
                val moshi = Moshi.Builder() // adapter
                    .add(KotlinJsonAdapterFactory())
                    .build()
                retrofitBuilder.apply {
//            baseUrl(localRepository.getBaseUrl())
                    baseUrl(ConstantsHelpers.BASE_URL)
                    addConverterFactory(MoshiConverterFactory.create(moshi))
                }.also {
                    val okHttpClientBuilder = OkHttpClient.Builder()

                    okHttpClientBuilder.writeTimeout(
                        ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                        TimeUnit.SECONDS
                    )
                    okHttpClientBuilder.readTimeout(
                        ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                        TimeUnit.SECONDS
                    )
                    okHttpClientBuilder.connectTimeout(
                        ConstantsHelpers.NETWORK_REQUEST_TIMEOUT,
                        TimeUnit.SECONDS
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
                weatherRepository = WeatherRepositoryImpl(
                    provideWeatherServiceApi(),
                    provideWeatherMockedServiceApi(context)
                )
            }
        }
        return weatherRepository!!
    }

}
