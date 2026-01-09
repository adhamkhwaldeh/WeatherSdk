package com.adham.weatherSdk.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.useCases.CurrentWeatherUseCase
import com.adham.weatherSdk.useCases.ForecastWeatherUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
//    private val _currentWeather= MutableSharedFlow<BaseState<Unit>>()
//    val currentWeather: SharedFlow<BaseState<Unit>> = _currentWeather

    /**
     * Load current weather
     *
     * @param city
     */
    fun loadCurrentWeather(city: String) {
        currentWeather.value = BaseState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            currentWeatherUseCase(city).collectLatest { result ->
                currentWeather.value = result
            }
        }
    }

    val forecast = MutableLiveData<BaseState<ForecastResponse>>(BaseState.Initial())
//    private val _forecast= MutableSharedFlow<BaseState<Unit>>()
//    val forecast: SharedFlow<BaseState<Unit>> = _forecast
    /**
     * Load forecast
     *
     * @param params
     */
    fun loadForecast(params: ForecastWeatherUseCase.ForecastWeatherUseCaseParams) {
        //        forecast.value = BaseState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            forecastWeatherUseCase(params).collectLatest { result ->
                forecast.value = result
            }
        }
    }

}