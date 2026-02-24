package com.adham.weatherSample.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adham.weatherSample.presentation.ui.navigations.NavigationItem
import com.adham.weatherSample.presentation.viewModels.AddressViewModel
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.domain.models.AddressModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinActivityViewModel

/**
 * App nav host
 *
 * @param modifier
 * @param navController
 * @param startDestination
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    addressViewModel: AddressViewModel = koinActivityViewModel(),
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.City.route,
) {
    val addressUiState by addressViewModel.uiState.collectAsState()

    LaunchedEffect(addressUiState.selectedAddress, addressUiState.isNavigateToForecast) {
        if (addressUiState.isNavigateToForecast) {
            addressUiState.selectedAddress?.let { address ->
                navController.navigate(
                    NavigationItem.weatherMapForecastRouteWithParams(
                        address,
                    ),
                )
            }
            addressViewModel.updateState { it.copy(isNavigateToForecast = false) }
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(NavigationItem.City.route) {
                WeatherMapEnterCityScreen()
            }

            composable(
                NavigationItem.Forecast.route,
                arguments =
                    listOf(
                        navArgument(NavigationItem.CITY_TAG) {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                val cityName = backStackEntry.arguments?.getString(NavigationItem.CITY_TAG) ?: ""
//                ForecastScreen(navController, cityName)
//                navController.navigate(NavigationItem.forecastRouteWithParams(current.cityName))
                ForecastScreen(cityName = cityName)
            }

            composable(
                NavigationItem.WeatherMapForecast.route,
                arguments =
                    listOf(
                        navArgument(NavigationItem.CITY_TAG) {
                            type = NavType.StringType
                        },
                    ),
            ) { backStackEntry ->
                val cityName = backStackEntry.arguments?.getString(NavigationItem.CITY_TAG) ?: ""
                val lat = backStackEntry.arguments?.getString(NavigationItem.LAT_TAG) ?: ""
                val lon = backStackEntry.arguments?.getString(NavigationItem.LON_TAG) ?: ""
                WeatherForecastScreen(
                    address = AddressModel(name = cityName, lat = lat, lon = lon),
                    navController = navController,
                )
            }
        }
    }
}
