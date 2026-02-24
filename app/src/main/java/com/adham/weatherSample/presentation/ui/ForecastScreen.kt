package com.adham.weatherSample.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adham.weatherSample.R
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.viewModels.WeatherViewModel
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse
import com.adham.weatherSdk.domain.useCases.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.helpers.DateHelpers
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.StatesLayoutCompose
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.StatesLayoutCustomActionInterface
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

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
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = koinViewModel(),
    weatherSDK: WeatherSDK = koinInject(),
) {
    val currentWeatherState = viewModel.currentWeather.observeAsState()
    val forecastState = viewModel.forecast.observeAsState()

    LaunchedEffect(cityName) {
        viewModel.loadCurrentWeather(cityName)
        viewModel.loadForecast(
            ForecastWeatherUseCaseParams(
                cityName,
                AppConstantsHelper.FORECAST_HOURS,
            ),
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            ForecastTopAppBar(onBack = {
            })
        },
        contentWindowInsets = WindowInsets(4.dp, 4.dp, 4.dp, 4.dp),
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(padding),
        ) {
            CurrentWeatherSection(
                cityName = cityName,
                state = currentWeatherState.value ?: BaseState.Initial(),
                onRetry = { viewModel.loadCurrentWeather(cityName) },
            )

            ForecastListSection(
                state = forecastState.value ?: BaseState.Initial(),
                onRetry = {
                    viewModel.loadForecast(
                        ForecastWeatherUseCaseParams(
                            cityName,
                            AppConstantsHelper.FORECAST_HOURS,
                        ),
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastTopAppBar(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        modifier = modifier.shadow(elevation = 2.dp),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        title = { Text(stringResource(R.string.TwentyFourHoursForecast)) },
    )
}

@Composable
fun CurrentWeatherSection(
    cityName: String,
    state: BaseState<CurrentWeatherResponse>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    StatesLayoutCompose(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
        customAction =
            object : StatesLayoutCustomActionInterface {
                override fun retry() = onRetry()
            },
        baseState = state,
    ) { response ->
        val model = response.data.firstOrNull()
        if (model != null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "${stringResource(R.string.TheWeatherIn)} $cityName ${stringResource(R.string.IS)}",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                )
                Text(
                    "${model.temp}${stringResource(R.string.celsius)}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                )
                Text(
                    text = model.weather.description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                )
                Text(
                    text = "${stringResource(R.string.At)} ${
                        DateHelpers.convertTimestampToLocalTime(
                            model.ts,
                        )
                    }",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        }
    }
}

@Composable
fun ForecastListSection(
    state: BaseState<ForecastResponse>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    StatesLayoutCompose(
        modifier =
            modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 8.dp),
        customAction =
            object : StatesLayoutCustomActionInterface {
                override fun retry() = onRetry()
            },
        baseState = state,
    ) { response ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxHeight()
                    .testTag(TestingConstantHelper.HOURLY_FORECAST_LIST),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            items(response.data.size) { index ->
                HourlyForecastItem(response.data[index])
            }
        }
    }
}
