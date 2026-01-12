package com.adham.weatherSample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adham.weatherSample.R
import com.adham.weatherSample.orm.Address
import com.adham.weatherSample.orm.WeatherDatabase
import com.adham.weatherSample.viewModels.WeatherViewModel
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

/**
 * A composable screen that allows users to enter a city name to retrieve weather forecasts.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCityScreen(
    modifier: Modifier = Modifier,
    weatherViewModel: WeatherViewModel = koinViewModel(),
) {
    val savedAddresses by weatherViewModel.savedAddresses.collectAsState(initial = emptyList())
    var cityName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = { EnterCityTopAppBar() },
        contentWindowInsets = WindowInsets(4.dp, 4.dp, 4.dp, 4.dp),
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
                cityName = cityName,
                onCityNameChange = {
                    cityName = it
                    isError = false
                },
                isError = isError,
                onSaveAddress = {
                    weatherViewModel.saveAddress(cityName) { isError = true }
                },
            )
            WeatherForecastSection(
                cityName = cityName,
                onShowError = { isError = true },
                onLaunchForecast = {
                    weatherViewModel.updateSdkStatus(WeatherSdkStatus.OnLaunchForecast(it))
                },
            )
            if (savedAddresses.isNotEmpty()) {
                SavedAddressesSection(
                    savedAddresses = savedAddresses,
                    onAddressClick = {
                        cityName = it
                        isError = false
                        weatherViewModel.updateSdkStatus(WeatherSdkStatus.OnLaunchForecast(it))
                    },
                    onDeleteAddress = { weatherViewModel.deleteAddress(it) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnterCityTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(R.string.ExampleApp)) },
        modifier = Modifier.shadow(elevation = 2.dp),
    )
}

@Composable
private fun WeatherForecastSection(
    cityName: String,
    onShowError: () -> Unit,
    onLaunchForecast: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.EnterTheCityNameForTheWeatherForecast),
            style = MaterialTheme.typography.labelLarge,
        )
        OutlinedButton(
            modifier =
                Modifier
                    .padding(8.dp)
                    .testTag("weatherForecastButton"),
            onClick = {
                if (cityName.isBlank()) {
                    onShowError()
                } else {
                    onLaunchForecast(cityName)
                }
            },
        ) {
            Text(text = stringResource(R.string.WeatherForecast))
        }
    }
}

@Composable
private fun CityInputSection(
    cityName: String,
    onCityNameChange: (String) -> Unit,
    isError: Boolean,
    onSaveAddress: () -> Unit,
) {
    OutlinedTextField(
        value = cityName,
        onValueChange = onCityNameChange,
        label = { Text(text = stringResource(R.string.EnterYourCityName)) },
        placeholder = { Text(text = stringResource(R.string.CityName)) },
        singleLine = true,
        isError = isError,
        leadingIcon = {
            IconButton(onClick = onSaveAddress, modifier = Modifier.testTag("saveAddressButton")) {
                Icon(
                    imageVector = Icons.Default.AddLocation,
                    contentDescription = "Save Address",
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        trailingIcon = {
            if (cityName.isNotEmpty()) {
                IconButton(onClick = { onCityNameChange("") }) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "Clear text",
                        tint = Color.Gray,
                    )
                }
            }
        },
        modifier =
            Modifier
                .fillMaxWidth()
                .testTag("cityInput"),
    )
}

@Composable
private fun SavedAddressesSection(
    savedAddresses: List<Address>,
    onAddressClick: (String) -> Unit,
    onDeleteAddress: (Address) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Saved Addresses",
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
                    onClick = { onAddressClick(address.name) },
                    onDelete = { onDeleteAddress(address) },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddressListItem(
    address: Address,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val dismissState =
        rememberSwipeToDismissBoxState(
            SwipeToDismissBoxValue.Settled,
            SwipeToDismissBoxDefaults.positionalThreshold,
        )

    if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
        onDelete()
        true
    } else {
        false
    }

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        backgroundContent = {
            val color =
                if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                    Color.Red
                } else {
                    Color.Transparent
                }
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(color)
                        .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
            }
        },
        enableDismissFromStartToEnd = false,
    ) {
        ListItem(
            headlineContent = { Text(address.name) },
            leadingContent = { Icon(Icons.Default.LocationCity, contentDescription = null) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
                    .testTag("addressItem_${address.name}"),
        )
    }
}
