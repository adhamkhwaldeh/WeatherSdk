package com.adham.weatherSample.presentation.viewModels

import android.app.Application
import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.local.database.WeatherDatabase
import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse
import com.adham.weatherSdk.domain.useCases.params.ForecastWeatherUseCaseParams
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
