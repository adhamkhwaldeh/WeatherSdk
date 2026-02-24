package com.adham.weatherSample.presentation.viewModels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adham.weatherSample.extensions.defaultSideEffect
import com.adham.weatherSample.presentation.states.WeatherUiState
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.domain.models.AddressModel
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherMapViewModel(
    application: Application,
    private val weatherSDK: WeatherSDK,
) : BaseRefactorViewModel(application) {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    /**
     * Load current weather
     *
     * @param address
     */
    fun loadCurrentWeather(address: AddressModel) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherSDK
                .currentWeatherMapUseCase(
                    address,
                ).collectLatest { result ->
                    if (result is BaseState.BaseStateLoadedSuccessfully ||
                        uiState.value.currentWeather !is BaseState.BaseStateLoadedSuccessfully
                    ) {
                        _uiState.update { it.copy(currentWeather = result) }
//                        _currentWeather.emit(result)
                    } else {
                        defaultSideEffect(result)
                    }
                    _uiState.update { it.copy(isLoadingCurrent = result is BaseState.Loading) }
                }
        }
    }

    /**
     * Load forecast
     *
     * @param address
     */
    fun loadForecast(address: AddressModel) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherSDK
                .forecastWeatherUseCase(
                    address,
                ).collectLatest { result ->
                    if (result is BaseState.BaseStateLoadedSuccessfully ||
                        uiState.value.forecast !is BaseState.BaseStateLoadedSuccessfully
                    ) {
                        _uiState.update { it.copy(forecast = result) }
                    } else {
                        defaultSideEffect(result)
                    }
                    _uiState.update { it.copy(isLoadingForeCast = result is BaseState.Loading) }
                }
        }
    }
}
