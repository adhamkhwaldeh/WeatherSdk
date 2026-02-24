package com.adham.weatherSdk.helpers

import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse
import com.adham.weatherSdk.data.remote.dtos.weatherMap.SysWeatherMapDto
import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.CityWeatherMapModel
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.MainWeatherMapModel
import com.adham.weatherSdk.domain.useCases.params.ForecastWeatherUseCaseParams
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

object DummyDataHelper {
    // #region general properties
    const val CITY_NAME = "munich"
    const val DEFAULT_HOURS = 24

    val noInternetException = UnknownHostException()

    val notAuthorizeException: Throwable =
        HttpException(
            Response.error<ResponseBody>(
                403,
                "some content".toResponseBody("plain/text".toMediaType()),
            ),
        )

    val internalServerError: Throwable =
        HttpException(
            Response.error<ResponseBody>(
                500,
                "some content".toResponseBody("plain/text".toMediaType()),
            ),
        )

    // #endregion

    // #region weather properties
    val weatherSuccessData =
        CurrentWeatherResponse(
            0,
            mutableListOf(),
        )

    val weatherSuccessState =
        BaseState.BaseStateLoadedSuccessfully(
            data = weatherSuccessData,
        )
    // #endregion

    // #region forecast properties
    val forecastWeatherUseCaseParams =
        ForecastWeatherUseCaseParams(
            city = CITY_NAME,
            DEFAULT_HOURS,
        )
    val forecastSuccessData =
        ForecastResponse(
            listOf(),
        )

    val forecastSuccessState =
        BaseState.BaseStateLoadedSuccessfully(
            data = forecastSuccessData,
        )
    // #endregion

    // #region weather properties

    val address = AddressModel(name = "London", lat = "", lon = "")
    const val API_KEY = "valid_key"

    val currentWeatherResponse: CurrentWeatherMapResponseModel =
        CurrentWeatherMapResponseModel(
            weather = listOf(),
            //    val base: String,
            main =
                MainWeatherMapModel(
                    temp = 0.0,
                    tempMin = 0.0,
                    tempMax = 0.0,
                    humidity = 0,
                    pressure = 0,
                ),
            sys = SysWeatherMapDto(),
            timezone = 0,
            id = 0,
            name = "",
        )

    val forecastWeatherResponse: ForecastWeatherMapResponseModel =
        ForecastWeatherMapResponseModel(
            cod = "",
            message = 0,
            cnt = 0,
            list = listOf(),
            city =
                CityWeatherMapModel(
                    name = "",
                    country = "",
                    id = 0,
                    timezone = 0,
                ),
        )

//    val weatherSuccessData =
//        CurrentWeatherResponse(
//            0,
//            mutableListOf(),
//        )
//
//    val weatherSuccessState =
//        BaseState.BaseStateLoadedSuccessfully(
//            data = weatherSuccessData,
//        )
    // #endregion

    // #region forecast properties
//    val forecastWeatherUseCaseParams =
//        ForecastWeatherUseCaseParams(
//            city = CITY_NAME,
//            DEFAULT_HOURS,
//        )
//    val forecastSuccessData =
//        ForecastResponse(
//            listOf(),
//        )
//
//    val forecastSuccessState =
//        BaseState.BaseStateLoadedSuccessfully(
//            data = forecastSuccessData,
//        )
    // #endregion
}
