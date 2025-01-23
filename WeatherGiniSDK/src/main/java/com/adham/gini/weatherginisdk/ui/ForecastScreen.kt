package com.adham.gini.weatherginisdk.ui


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adham.gini.weatherginisdk.R
import com.adham.gini.weatherginisdk.WeatherGiniSDKBuilder
import com.adham.gini.weatherginisdk.base.stateLayout.StatesLayoutCompose
import com.adham.gini.weatherginisdk.base.stateLayout.StatesLayoutCustomActionInterface
import com.adham.gini.weatherginisdk.data.states.WeatherSdkStatus
import com.adham.gini.weatherginisdk.helpers.DateHelpers
import com.adham.gini.weatherginisdk.useCases.ForecastWeatherUseCase
import com.adham.gini.weatherginisdk.viewModels.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * Forecast screen
 *
 * @param cityName
 * @param viewModel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(
    cityName: String,
    viewModel: WeatherViewModel = koinViewModel()
) {

    val currentWeatherState = viewModel.currentWeather.observeAsState()

    val forecastState = viewModel.forecast.observeAsState()

    LaunchedEffect({}) {
        viewModel.loadCurrentWeather(cityName)
        viewModel.loadForecast(
            ForecastWeatherUseCase.ForecastWeatherUseCaseParams(
                cityName, 24
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        WeatherGiniSDKBuilder.sdkStatus.value = WeatherSdkStatus.OnFinish
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Replace with your desired icon
                            contentDescription = "Back",
//                            tint = Color.White
                        )
                    }
                },
                title = { Text(stringResource(R.string.TwentyFourHoursForecast)) },
                modifier = Modifier.shadow(elevation = 2.dp)
            )
        },
        contentWindowInsets = WindowInsets(4.dp, 4.dp, 4.dp, 4.dp)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            StatesLayoutCompose(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .wrapContentHeight(),
                customAction = object : StatesLayoutCustomActionInterface {
                    override fun retry() {
                        viewModel.loadCurrentWeather(cityName)
                    }
                },
                baseState = currentWeatherState.value!!
            ) {
                val model = it.data.firstOrNull()
                if (model != null) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "${stringResource(R.string.TheWeatherIn)} $cityName ${
                                stringResource(
                                    R.string.IS
                                )
                            }",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                        Text(
                            "${model.temp}${stringResource(R.string.celsius)}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = model.weather.description,
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
                        )
                        Text(
                            text = "${stringResource(R.string.At)} ${
                                DateHelpers.convertTimestampToLocalTime(
                                    model.ts
                                )
                            }",
//                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                        )

                    }
                }
            }

            StatesLayoutCompose(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 8.dp),
                customAction = object : StatesLayoutCustomActionInterface {
                    override fun retry() {
                        viewModel.loadForecast(
                            ForecastWeatherUseCase.ForecastWeatherUseCaseParams(
                                cityName, 24
                            )
                        )
                    }
                },
                baseState = forecastState.value!!
            ) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(it.data) { item ->
                        HourlyForecastItem(item)
                    }
                }
            }

        }
    }
}