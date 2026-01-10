package com.adham.weatherSample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.adham.weatherSample.R
import com.adham.weatherSample.orm.Address
import com.adham.weatherSample.orm.WeatherDatabase
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

/**
 * Enter city screen
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCityScreen(weatherSDK: WeatherSDK = koinInject()) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val addressDao = remember { WeatherDatabase.getDatabase(context).addressDao() }
    val savedAddresses by addressDao.loadAllDataFlow().collectAsState(initial = emptyList())

    var cityName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.ExampleApp)) },
                modifier = Modifier.shadow(elevation = 2.dp)
            )
        },
        contentWindowInsets = WindowInsets(4.dp, 4.dp, 4.dp, 4.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {

            OutlinedTextField(
                value = cityName,
                onValueChange = {
                    cityName = it
                    isError = false
                },
                label = { Text(text = stringResource(R.string.EnterYourCityName)) },
                placeholder = { Text(text = stringResource(R.string.CityName)) },
                singleLine = true,
                isError = isError,
                leadingIcon = {
                    IconButton(onClick = {
                        if (cityName.isNotBlank()) {
                            scope.launch(Dispatchers.IO) {
                                val trimmedName = cityName.trim()
                                val existing = addressDao.findByName(trimmedName)
                                if (existing == null) {
                                    addressDao.insert(Address(name = trimmedName))
                                }
                            }
                        } else {
                            isError = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.AddLocation,
                            contentDescription = "Save Address",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                trailingIcon = {
                    if (cityName.isNotEmpty()) {
                        IconButton(onClick = { cityName = "" }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = "Clear text",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.EnterTheCityNameForTheWeatherForecast),
                style = MaterialTheme.typography.labelLarge
            )

            OutlinedButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    if (cityName.isBlank()) {
                        isError = true
                    } else {
                        weatherSDK.sdkStatus.value =
                            WeatherSdkStatus.OnLaunchForecast(cityName)
                    }
                },
            ) {
                Text(text = stringResource(R.string.WeatherForecast))
            }

            if (savedAddresses.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Saved Addresses",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(
                        items = savedAddresses,
                        key = { it.id }
                    ) { address ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                if (it == SwipeToDismissBoxValue.EndToStart) {
                                    scope.launch(Dispatchers.IO) {
                                        addressDao.delete(address)
                                    }
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            backgroundContent = {
                                val color = when (dismissState.dismissDirection) {
                                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                                    else -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 16.dp),
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.White
                                    )
                                }
                            },
                            enableDismissFromStartToEnd = false
                        ) {
                            ListItem(
                                headlineContent = { Text(address.name) },
                                leadingContent = {
                                    Icon(Icons.Default.LocationCity, contentDescription = null)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        cityName = address.name
                                        weatherSDK.sdkStatus.value =
                                            WeatherSdkStatus.OnLaunchForecast(address.name)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
