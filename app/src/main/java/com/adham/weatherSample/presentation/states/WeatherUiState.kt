package com.adham.weatherSample.presentation.states

import com.adham.dvt.commonlibrary.base.states.BaseState
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel

data class WeatherUiState(
    val currentWeather: BaseState<CurrentWeatherMapResponseModel> = BaseState.Initial(),
    val forecast: BaseState<ForecastWeatherMapResponseModel> = BaseState.Initial(),
    // the flags below for loading after fetching the data from the cache
    val isLoadingCurrent: Boolean = false,
    val isLoadingForeCast: Boolean = false,
) {
    val isLoading: Boolean
        get() {
            return isLoadingCurrent || isLoadingForeCast
        }
}
