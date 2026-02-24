package com.adham.weatherSample.presentation.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adham.dvt.weathersdk.data.remote.mappers.toAddressModel
import com.adham.dvt.weathersdk.domain.models.AddressModel
import com.adham.dvt.weathersdk.domain.models.GeoByNameModel
import com.adham.weatherSample.R
import com.adham.weatherSample.extensions.showToast
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.ui.components.AddressListItem
import com.adham.weatherSample.presentation.ui.components.LocationPickerDialog
import com.adham.weatherSample.presentation.viewModels.AddressGeoViewModel
import com.adham.weatherSample.presentation.viewModels.AddressViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.viewmodel.koinActivityViewModel
import kotlin.collections.isNotEmpty
import kotlin.text.isNotEmpty

/**
 * A composable screen that allows users to enter a city name to retrieve weather forecasts.
 */
@Suppress("LongMethod")
// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherMapEnterCityScreen(
    modifier: Modifier = Modifier,
    addressViewModel: AddressViewModel = koinActivityViewModel(),
    addressGeoViewModel: AddressGeoViewModel = koinViewModel(),
) {
    val uiState by addressViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        addressViewModel.loadSavedAddresses()
    }

    var showSheet by remember { mutableStateOf(false) }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { granted ->
            if (granted) {
                showSheet = true
            }
        }

    Scaffold(
        modifier = modifier,
        topBar = { EnterCityTopAppBar() },
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            CityInputSection(
                onCitySelected = { address ->
                    addressViewModel.updateSelectedAddress(address?.toAddressModel(), false)
                },
                openMap = {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
            )

            WeatherForecastSection(
                selectedAddress = uiState.selectedAddress,
                onLaunchForecast = {
                    if (uiState.selectedAddress == null) {
                        addressViewModel.showToast(R.string.typeTheNameOrSelectTheLocation)
                    }
                    uiState.selectedAddress?.let { address ->
                        if (it) {
                            addressViewModel.saveAddress(address)
                        } else {
                            addressViewModel.updateSelectedAddress(address)
                        }
                    }
                },
            )

            if (uiState.savedAddresses.isNotEmpty()) {
                SavedAddressesSection(
                    savedAddresses = uiState.savedAddresses,
                    onAddressClick = { address ->
                        addressViewModel.updateSelectedAddress(address)
                    },
                    onDeleteAddress = { addressViewModel.deleteAddress(it) },
                )
            }

            LocationPickerDialog(
                showSheet = showSheet,
                onDismiss = { showSheet = false },
                onLocationSelected = { address ->
                    if (address.name.isEmpty()) {
                        addressGeoViewModel.getNameByGeo(
                            lat = address.lat.toString(),
                            lon = address.lon.toString(),
                        )
                    } else {
                        addressGeoViewModel.clearGeoWithNameData()
                        addressViewModel.updateSelectedAddress(address.toAddressModel(), false)
                        showSheet = false
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnterCityTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        modifier = Modifier.shadow(elevation = 2.dp),
    )
}

@Composable
private fun WeatherForecastSection(
    modifier: Modifier = Modifier,
    selectedAddress: AddressModel?,
    onLaunchForecast: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        var checked by remember { mutableStateOf(false) }

        if (selectedAddress != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                )
                Text(
                    text = stringResource(R.string.SaveTheAddress, selectedAddress.name),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        OutlinedButton(
            modifier =
                Modifier
                    .padding(8.dp)
                    .testTag(TestingConstantHelper.WEATHER_FORECAST_BUTTON),
            onClick = {
                onLaunchForecast(checked)
            },
        ) {
            Text(text = stringResource(R.string.WeatherForecast))
        }
    }
}

// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityInputSection(
    addressGeoViewModel: AddressGeoViewModel = koinViewModel(),
    openMap: () -> Unit,
    onCitySelected: (GeoByNameModel?) -> Unit,
) {
    val uiState by addressGeoViewModel.uiState.collectAsState()

    ExposedDropdownMenuBox(
        expanded = uiState.isExpanded && uiState.suggestions.isNotEmpty(),
        onExpandedChange = {
            addressGeoViewModel.updateState { state -> state.copy(isExpanded = it) }
        },
    ) {
        OutlinedTextField(
            value = uiState.query,
            onValueChange = {
                addressGeoViewModel.updateState { state ->
                    state.copy(
                        isExpanded = true,
                        query = it,
                    )
                }
                addressGeoViewModel.getGeoByName(it)
            },
            label = { Text(text = stringResource(R.string.EnterYourCityName)) },
            placeholder = { Text(text = stringResource(R.string.CityName)) },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {
                        openMap()
                        addressGeoViewModel.clearGeoWithNameData()
                    },
                    modifier = Modifier.testTag(TestingConstantHelper.SAVE_ADDRESS_BUTTON),
                ) {
                    Icon(
                        imageVector = Icons.Default.AddLocation,
                        contentDescription = "Save Address",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            trailingIcon = {
                if (uiState.query.isNotEmpty()) {
                    IconButton(onClick = {
                        addressGeoViewModel.updateState { state ->
                            state.copy(
                                isExpanded = true,
                                query = "",
                                suggestions = listOf(),
                            )
                        }
                        onCitySelected(null)
                        addressGeoViewModel.clearGeoWithNameData()
                    }) {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                            contentDescription = TestingConstantHelper.CLEAR_TEXT,
                            tint = Color.Gray,
                        )
                    }
                }
            },
            modifier =
                Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .testTag(TestingConstantHelper.CITY_INPUT_TAG),
        )

        ExposedDropdownMenu(
            expanded = uiState.isExpanded && uiState.suggestions.isNotEmpty(),
            onDismissRequest = {
                addressGeoViewModel.updateState { state -> state.copy(isExpanded = false) }
            },
        ) {
            uiState.suggestions.forEach { selection ->
                DropdownMenuItem(
                    text = { Text(selection.name) },
                    onClick = {
                        onCitySelected(selection)
                        addressGeoViewModel.updateState { state ->
                            state.copy(
                                isExpanded = false,
                                query = selection.name,
                            )
                        }
                    },
                )
            }
        }
    }

    if (uiState.isLoading) {
        LinearProgressIndicator(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
        )
    }
}

@Composable
private fun SavedAddressesSection(
    savedAddresses: List<AddressModel>,
    onAddressClick: (AddressModel) -> Unit,
    onDeleteAddress: (AddressModel) -> Unit,
    addressViewModel: AddressViewModel = koinActivityViewModel(),
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.SavedAddresses),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth(),
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .testTag("savedAddressesList"),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(items = savedAddresses, key = { it.id }) { address ->
                AddressListItem(
                    address = address,
                    onClick = { onAddressClick(address) },
                    onDelete = { onDeleteAddress(address) },
                    onSetDefault = {
                        addressViewModel.saveAddress(address.copy(isDefault = true))
                        onAddressClick(address)
                    },
                )
            }
        }
    }
}
