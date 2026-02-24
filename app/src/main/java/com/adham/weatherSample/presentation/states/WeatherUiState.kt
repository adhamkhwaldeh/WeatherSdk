package com.adham.weatherSample.presentation.states

import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState

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
