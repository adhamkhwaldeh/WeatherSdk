package com.adham.weatherSdk.di

//import com.adham.weatherSdk.helpers.ConstantsHelpers
//import com.adham.weatherSdk.networking.WeatherMockedServiceApi
//import com.adham.weatherSdk.networking.WeatherServiceApi
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import org.koin.dsl.module
//import retrofit2.Retrofit
//import retrofit2.converter.moshi.MoshiConverterFactory
//import java.util.concurrent.TimeUnit
//
//val networkModule = module {
//
//    single { OkHttpClient.Builder() }
//
//    single {
//
//        val logger = HttpLoggingInterceptor().apply {
//
//            level = HttpLoggingInterceptor.Level.BODY
//
////            if (BuildConfig.DEBUG)
////                HttpLoggingInterceptor.Level.BODY
////            else
////                HttpLoggingInterceptor.Level.NONE
//
//        }
//
//        val retrofitBuilder = Retrofit.Builder()
//        val moshi = Moshi.Builder() // adapter
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        retrofitBuilder.apply {
////            baseUrl(localRepository.getBaseUrl())
//            baseUrl(ConstantsHelpers.baseUrl)
//            addConverterFactory(MoshiConverterFactory.create(moshi))
//        }.also {
//            val okHttpClientBuilder = OkHttpClient.Builder()
//
//            okHttpClientBuilder.writeTimeout(10, TimeUnit.SECONDS)
//            okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
//            okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
//
//            okHttpClientBuilder.addInterceptor(logger)
//            it.client(okHttpClientBuilder.build())
//        }
//
//        retrofitBuilder.build()
//    }
//
//    single {
//        get<Retrofit>().create(WeatherServiceApi::class.java)
//    }
//
//    single {
//        WeatherMockedServiceApi(get())
//    }
//
//}