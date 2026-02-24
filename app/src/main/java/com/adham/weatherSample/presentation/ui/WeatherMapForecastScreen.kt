package com.adham.weatherSample.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adham.weatherSample.R
import com.adham.weatherSample.extensions.getBackground
import com.adham.weatherSample.extensions.getColor
import com.adham.weatherSample.helpers.DimensionsHelper
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.ui.components.SettingsBottomSheet
import com.adham.weatherSample.presentation.ui.components.WeatherForecastItem
import com.adham.weatherSample.presentation.ui.components.WeatherMainItem
import com.adham.weatherSample.presentation.ui.providers.LocalWeatherThemeController
import com.adham.weatherSample.presentation.viewModels.WeatherMapViewModel
import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.extensions.kelvinToCelsiusInt
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.StatesLayoutCompose
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.StatesLayoutCustomActionInterface
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import org.koin.androidx.compose.koinViewModel

/**
 * Forecast screen
 *
 * @param address
 * @param viewModel
 */
@Suppress("LongMethod")
// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastScreen(
    address: AddressModel,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: WeatherMapViewModel = koinViewModel(),
) {
//    val currentWeatherState by viewModel.currentWeather.collectAsState(BaseState.Initial())

//    val forecastState by viewModel.forecast.collectAsState(BaseState.Initial())

    val uiState by viewModel.uiState.collectAsState()

    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCurrentWeather(address)
        viewModel.loadForecast(address)
    }

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = Color.Transparent,
    ) { padding ->

        val weatherThemeController = LocalWeatherThemeController.current

        Box(Modifier.fillMaxSize()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(
                            colorResource(
                                weatherThemeController
                                    .weatherTheme
                                    .getColor(),
                            ),
                        ).padding(padding),
            ) {
                CurrentWeatherSection(
                    modifier = Modifier.weight(DimensionsHelper.CURRENT_WEATHER_HEIGHT_TAG),
                    state = uiState.currentWeather,
                    onRetry = {
                        viewModel.loadCurrentWeather(address)
                    },
                )

                HorizontalDivider(color = Color.White, thickness = 1.dp)

                ForecastListSection(
                    modifier = Modifier.weight(DimensionsHelper.FORECAST_LIST_HEIGHT_TAG),
                    state = uiState.forecast,
                    onRetry = {
                        viewModel.loadForecast(address)
                    },
                )
            }
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                CenterAlignedTopAppBar(
                    colors =
                        TopAppBarColors(
                            Color.Transparent,
                            Color.Transparent,
                            Color.Transparent,
                            Color.White,
                            Color.White,
                            Color.White,
                        ),
                    title = {
                        Text(
                            address.name,
                            style = MaterialTheme.typography.titleLarge,
                            softWrap = true,
                            maxLines = 1,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            showSheet = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Save Address",
                                tint = Color.White,
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.loadCurrentWeather(address)
                            viewModel.loadForecast(address)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Save Address",
                                tint = Color.White,
                            )
                        }
                    },
                )
                if (uiState.isLoading) {
                    LinearProgressIndicator(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomEnd)
                                .padding(top = 4.dp),
                    )
                }
            }
        }

        SettingsBottomSheet(
            showSheet = showSheet,
            navController = navController,
            onDismiss = {
                showSheet = false
            },
        )
    }
}

// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@Suppress("LongMethod")
@Composable
fun CurrentWeatherSection(
    state: BaseState<CurrentWeatherMapResponseModel>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val weatherThemeController = LocalWeatherThemeController.current

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
        weatherThemeController.weatherTheme = response.weatherMain

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = response.weatherMain.getBackground()),
                    contentDescription = "My image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "${response.main.temp.kelvinToCelsiusInt()}${stringResource(R.string.celsiusSign)}",
                        style =
                            MaterialTheme.typography.displayLarge
                                .copy(fontWeight = FontWeight.Bold, color = Color.White),
                    )
                    Text(
                        response.weather.firstOrNull()?.main ?: "",
                        style =
                            MaterialTheme.typography.displayMedium.copy(
                                color = Color.White,
                            ),
                    )
                }
            }
            Row(
                modifier =
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(4.dp)
                        .background(colorResource(response.weatherMain.getColor())),
            ) {
                WeatherMainItem(
                    modifier = Modifier.weight(1f),
                    temp = response.main.tempMin,
                    label = stringResource(R.string.min),
                )
                WeatherMainItem(
                    modifier = Modifier.weight(1f),
                    temp = response.main.temp,
                    label = stringResource(R.string.current),
                )
                WeatherMainItem(
                    modifier = Modifier.weight(1f),
                    temp = response.main.tempMax,
                    label = stringResource(R.string.max),
                )
            }
        }
    }
}

@Composable
fun ForecastListSection(
    state: BaseState<ForecastWeatherMapResponseModel>,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val weatherThemeController = LocalWeatherThemeController.current

    StatesLayoutCompose(
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
//            modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .padding(top = 8.dp),
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
                    .background(
                        colorResource(
                            weatherThemeController
                                .weatherTheme
                                .getColor(),
                        ),
                    ).testTag(TestingConstantHelper.HOURLY_FORECAST_LIST),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 16.dp),
        ) {
            items(response.days.size) { index ->
                WeatherForecastItem(response.days[index])
            }
        }
    }
}
