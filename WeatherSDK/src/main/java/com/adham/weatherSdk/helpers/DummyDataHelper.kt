package com.adham.weatherSdk.helpers

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.useCases.ForecastWeatherOldUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

object DummyDataHelper {

    //#region general properties
    const val cityName = "munich"
    const val defaultHours = 24

    val noInternetException = UnknownHostException()

    val notAuthorizeException = HttpException(
        Response.error<ResponseBody>(
            403,
            "some content".toResponseBody("plain/text".toMediaType())
        )
    )

    val internalServerError = HttpException(
        Response.error<ResponseBody>(
            500,
            "some content".toResponseBody("plain/text".toMediaType())
        )
    )

    //#endregion

    //#region weather properties
    val weatherSuccessData = CurrentWeatherResponse(
        0,
        mutableListOf()
    )

    val weatherSuccessState = BaseState.BaseStateLoadedSuccessfully(
        data = weatherSuccessData
    )
    //#endregion


    //#region forecast properties
    val forecastWeatherUseCaseParams = ForecastWeatherOldUseCase.ForecastWeatherUseCaseParams(
        city = cityName, defaultHours
    )
    val forecastSuccessData = ForecastResponse(
        listOf()
    )

    val forecastSuccessState = BaseState.BaseStateLoadedSuccessfully(
        data = forecastSuccessData
    )
    //#endregion


}