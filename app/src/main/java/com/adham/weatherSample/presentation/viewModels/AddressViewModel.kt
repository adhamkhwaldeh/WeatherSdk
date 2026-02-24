package com.adham.weatherSample.presentation.viewModels

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.adham.dvt.commonlibrary.base.BaseRefactorViewModel
import com.adham.dvt.commonlibrary.base.states.BaseState
import com.adham.weatherSample.presentation.states.AddressState
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.domain.models.AddressModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Weather view model
 *
 * @property application
 * @property weatherSDK
 * @constructor Create empty Weather view model
 */
class AddressViewModel(
    private val application: Application,
    private val weatherSDK: WeatherSDK,
) : BaseRefactorViewModel(application) {
    init {
        viewModelScope.launch {
            weatherSDK.getDefaultAddressUseCase().collect {
                _uiState.emit(AddressState(selectedAddress = it))
            }
        }
    }

    private val _uiState = MutableStateFlow(AddressState())
    val uiState: StateFlow<AddressState> = _uiState.asStateFlow()

    fun updateState(function: (AddressState) -> AddressState) {
        _uiState.update { function(it) }
    }

    fun updateSelectedAddress(
        address: AddressModel?,
        navigate: Boolean = true,
    ) {
        updateState {
            it.copy(selectedAddress = address, isNavigateToForecast = navigate)
        }
    }

    fun loadSavedAddresses() {
        viewModelScope.launch {
            weatherSDK.allSavedAddresses().collect { res ->
                _uiState.update { it.copy(savedAddresses = res) }
            }
        }
    }

    fun saveAddress(data: AddressModel) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherSDK
                .insertOrUpdateAddressWithDefaultUseCase(
                    data,
                ).collectLatest {
                    if (it is BaseState.BaseStateLoadedSuccessfully) {
                        _uiState.emit(AddressState(selectedAddress = it.data))
                    }
                }
        }
    }

    fun deleteAddress(address: AddressModel) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherSDK.deleteAddressUseCase(address).collectLatest {}
        }
    }
}
