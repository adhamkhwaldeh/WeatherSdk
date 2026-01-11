package com.adham.weatherSample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.adham.weatherSample.ui.AppNavHost
import com.adham.weatherSample.ui.theme.WeatherSDKTheme

/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherSDKTheme {

                AppNavHost(
                    modifier = Modifier.fillMaxSize(),
                )

            }
        }
    }
}


/**
 * Greeting preview
 *
 */
@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {

    WeatherSDKTheme {
        AppNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }

}
