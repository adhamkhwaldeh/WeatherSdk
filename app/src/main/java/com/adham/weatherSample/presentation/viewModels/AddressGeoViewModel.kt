package com.adham.weatherSample.presentation.viewModels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adham.weatherSample.extensions.defaultSideEffect
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.presentation.states.AddressGeoState
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.useCases.params.GeoByNameWeatherMapUseCaseParams
import com.adham.weatherSdk.domain.useCases.params.NameByGeoWeatherMapUseCaseParams
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddressGeoViewModel(
    application: Application,
    private val weatherSDK: WeatherSDK,
) : BaseRefactorViewModel(application) {
    private val _uiState = MutableStateFlow(AddressGeoState())
    val uiState: StateFlow<AddressGeoState> = _uiState.asStateFlow()

    private var geoJob: Job? = null

    fun getGeoByName(cityName: String) {
        geoJob?.cancel()
        geoJob =
            viewModelScope.launch(Dispatchers.IO) {
                delay(AppConstantsHelper.DEBOUNCE_DURATION)
                _uiState.update { it.copy(isLoading = true) }
                weatherSDK
                    .geoByNameWeatherMapUseCase(GeoByNameWeatherMapUseCaseParams(q = cityName))
                    .collectLatest { result ->
                        updateState(result)
                        defaultSideEffect(result)
                    }
            }
    }

    fun getNameByGeo(
        lat: String,
        lon: String,
    ) {
        geoJob?.cancel()
        geoJob =
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.update { it.copy(isLoading = true) }
                weatherSDK
                    .nameByGeoWeatherMapUseCas(
                        NameByGeoWeatherMapUseCaseParams(
                            lat = lat,
                            lon = lon,
                        ),
                    ).collectLatest { result ->
                        updateState(result)
                        defaultSideEffect(result)
                    }
            }
    }

    fun updateState(result: BaseState<Set<GeoByNameModel>>) {
        val suggestion =
            if (result is BaseState.BaseStateLoadedSuccessfully<Set<GeoByNameModel>>) {
                result.data.toList()
            } else {
                listOf()
            }
        updateState {
            it.copy(
                suggestions = suggestion,
                isExpanded = suggestion.isNotEmpty(),
                isLoading = result is BaseState.Loading,
                query =
                    if (result is BaseState.Initial) {
                        ""
                    } else {
                        _uiState.value.query
                    },
            )
        }
    }

    fun updateState(function: (AddressGeoState) -> AddressGeoState) {
        _uiState.update { function(it) }
    }

    fun clearGeoWithNameData() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState(BaseState.Initial())
        }
    }
}
