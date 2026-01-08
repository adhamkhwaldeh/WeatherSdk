package com.adham.weatherSdk.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.useCases.CurrentWeatherUseCase
import com.adham.weatherSdk.useCases.ForecastWeatherUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState

/**
 * Weather view model
 *
 * @property application
 * @property currentWeatherUseCase
 * @property forecastWeatherUseCase
 * @constructor Create empty Weather view model
 */
class WeatherViewModel(
    private val application: Application,
    private val currentWeatherUseCase: CurrentWeatherUseCase,
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
) : BaseRefactorViewModel(application) {

    val currentWeather = MutableLiveData<BaseState<CurrentWeatherResponse>>(BaseState.Initial())

    /**
     * Load current weather
     *
     * @param city
     */
    fun loadCurrentWeather(city: String) {
        currentWeather.value = BaseState.Loading()
        currentWeatherUseCase(city) {
            currentWeather.value = it
        }
    }

    val forecast = MutableLiveData<BaseState<ForecastResponse>>(BaseState.Initial())

    /**
     * Load forecast
     *
     * @param params
     */
    fun loadForecast(params: ForecastWeatherUseCase.ForecastWeatherUseCaseParams) {
//        forecast.value = BaseState.Loading()
        forecastWeatherUseCase(params) {
            forecast.value = it
        }
    }

}