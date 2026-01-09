package com.adham.weatherSdk.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember


import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.adham.weatherSdk.R
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import org.koin.compose.koinInject

/**
 * Enter city screen
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCityScreen(weatherSDK: WeatherSDK = koinInject()) {

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
                .fillMaxWidth()
                .padding(padding),
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
        }
    }

}