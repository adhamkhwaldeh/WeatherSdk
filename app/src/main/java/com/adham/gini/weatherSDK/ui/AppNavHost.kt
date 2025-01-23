package com.adham.gini.weatherSDK.ui

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
import com.adham.gini.weatherginisdk.WeatherGiniSDKBuilder
import com.adham.gini.weatherginisdk.data.states.WeatherSdkStatus
import com.adham.gini.weatherginisdk.ui.ForecastScreen
import com.adham.gini.weatherginisdk.ui.navigations.NavigationItem


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
) {

    val sdkStatus = WeatherGiniSDKBuilder.sdkStatus.observeAsState()
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
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = startDestination
        ) {
            composable(NavigationItem.City.route) {
                EnterCityScreen()
            }
            composable(
                NavigationItem.Forecast.route,
                arguments = listOf(navArgument(NavigationItem.CityTag) {
                    type = NavType.StringType
                }),
            ) { backStackEntry ->
                val cityName = backStackEntry.arguments?.getString(NavigationItem.CityTag)!!
//                ForecastScreen(navController, cityName)
                ForecastScreen(cityName)
            }
        }
    }
}