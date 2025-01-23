package com.adham.gini.weatherSDK.ui

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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.adham.gini.weatherginisdk.WeatherGiniSDKBuilder
import com.adham.gini.weatherginisdk.data.states.WeatherSdkStatus
import com.adham.gini.weatherginisdk.ui.navigations.NavigationItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterCityScreen() {

    var cityName by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Example App") },
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
                label = { Text(text = "Enter your city name") },
                placeholder = { Text(text = "City Name") },
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
                text = "Enter the city name for the weather forecast",
                style = MaterialTheme.typography.labelLarge
            )
            OutlinedButton(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    if (cityName.isBlank()) {
                        isError = true
                    } else {
                        WeatherGiniSDKBuilder.sdkStatus.value =
                            WeatherSdkStatus.OnLaunchForecast(cityName)
                    }
                },
            ) {
                Text(text = "Weather forecast")
            }
        }
    }

}