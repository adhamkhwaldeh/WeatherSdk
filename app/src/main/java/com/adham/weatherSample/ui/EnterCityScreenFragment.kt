package com.adham.weatherSample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.adham.weatherSample.ui.theme.WeatherSDKTheme

/**
 * Enter city screen fragment
 *
 * @constructor Create empty Enter city screen fragment
 */
class EnterCityScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val composeView = ComposeView(requireContext())
        composeView.isTransitionGroup = true
        composeView.setContent {
            WeatherSDKTheme {
//                AppNavHost(
//                    modifier = Modifier.fillMaxSize(),
//                )

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    EnterCityScreen()
                }
            }
        }
        return composeView
    }
}
