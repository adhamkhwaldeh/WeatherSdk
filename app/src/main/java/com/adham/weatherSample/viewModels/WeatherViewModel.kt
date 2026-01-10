package com.adham.weatherSample.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Weather view model
 *
 * @property application
 * @property weatherSDK
 * @constructor Create empty Weather view model
 */
class WeatherViewModel(
    private val application: Application,
    private val weatherSDK: WeatherSDK,
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
            weatherSDK.currentWeatherUseCase(city).collectLatest { result ->
//                currentWeather.value = result
                currentWeather.postValue(result)
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
    fun loadForecast(params: ForecastWeatherUseCaseParams) {
        //        forecast.value = BaseState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            weatherSDK.forecastWeatherUseCase(params).collectLatest { result ->
//                forecast.value = result
                forecast.postValue(result)
            }
        }
    }

}