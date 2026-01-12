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

/**
 * Forecast screen fragment
 *
 * @constructor Create empty Forecast screen fragment
 */
class ForecastScreenFragment : Fragment() {
    private val cityName: String
        get() {
            return arguments?.getString(CITY_NAME_TAG, "") ?: ""
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val composeView = ComposeView(requireContext())
        composeView.isTransitionGroup = true
        composeView.setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                ForecastScreen(cityName = cityName)
            }
        }
        return composeView
    }

    companion object {
        private const val CITY_NAME_TAG = "City"

        fun newInstance(cityName: String): ForecastScreenFragment {
            val f = ForecastScreenFragment()
            val bundle = Bundle()
            bundle.putString(CITY_NAME_TAG, cityName)
            f.arguments = bundle
            return f
        }
    }
}
