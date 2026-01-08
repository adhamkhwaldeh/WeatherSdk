package com.adham.weatherSdk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.adham.weatherSdk.ui.AppNavHost
import com.adham.weatherSdk.ui.theme.WeatherSDKTheme

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
fun GreetingPreview() {

    WeatherSDKTheme {
        AppNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = rememberNavController()
        )
    }

}