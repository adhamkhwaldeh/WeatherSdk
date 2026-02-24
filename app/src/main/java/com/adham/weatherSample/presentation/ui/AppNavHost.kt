package com.adham.weatherSample.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.adham.weatherSample.presentation.ui.navigations.NavigationItem
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import org.koin.compose.koinInject

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
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavigationItem.City.route,
    weatherSDK: WeatherSDK = koinInject(),
) {
    val sdkStatus = weatherSDK.sdkStatus.observeAsState()
    LaunchedEffect(sdkStatus.value) {
        val current = sdkStatus.value
        if (current is WeatherSdkStatus.OnFinish) {
            navController.navigateUp()
        } else if (current is WeatherSdkStatus.OnLaunchForecast) {
            navController.navigate(NavigationItem.forecastRouteWithParams(current.cityName))
        }
    }

    // A surface container using the 'background' color from the theme
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
        ) {
            composable(NavigationItem.City.route) {
                EnterCityScreen()
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
                ForecastScreen(cityName = cityName)
            }
        }
    }
}
