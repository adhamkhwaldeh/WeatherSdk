package com.adham.weatherSample.presentation.viewModels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adham.weatherSample.extensions.defaultSideEffect
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.presentation.states.PlaceState
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.models.PlaceModel
import com.github.adhamkhwaldeh.commonlibrary.base.BaseRefactorViewModel
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class PlacesViewModel(
    application: Application,
    private val weatherSdk: WeatherSDK,
) : BaseRefactorViewModel(application) {
    private val _uiState = MutableStateFlow(PlaceState())
    val uiState: StateFlow<PlaceState> = _uiState.asStateFlow()
    private var geoJob: Job? = null

    init {
        viewModelScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .debounce(AppConstantsHelper.DEBOUNCE_DURATION)
                .collect { query ->
                    if (query.isNotBlank() && query.length >= AppConstantsHelper.QUERY_LENGTH) {
                        search(query)
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery, isExpanded = true) }
    }

    fun search(newQuery: String) {
        geoJob?.cancel()
        geoJob =
            viewModelScope.launch {
                weatherSdk.placesSearchUseCase(newQuery).collectLatest { res ->
                    if (res is BaseState.BaseStateLoadedSuccessfully<List<PlaceModel>>) {
                        _uiState.update { it.copy(results = res.data) }
                    }
                    defaultSideEffect(res)
                }
            }
    }

    fun loadPlaceDetails(placeId: String) {
        viewModelScope.launch {
            weatherSdk.placesPlaceByIdUseCase(placeId).collectLatest { res ->
                if (res is BaseState.BaseStateLoadedSuccessfully<GeoByNameModel>) {
                    _uiState.update { it.copy(selectedPlace = res.data) }
                }
                defaultSideEffect(res)
            }
        }
    }

    fun clearData() {
        _uiState.update { it.copy(selectedPlace = null, query = "") }
    }

    fun updateState(function: (PlaceState) -> PlaceState) {
        _uiState.update { function(it) }
    }
}
