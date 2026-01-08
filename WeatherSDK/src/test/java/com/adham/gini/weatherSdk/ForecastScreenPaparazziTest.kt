package com.adham.weatherSdk

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.adham.weatherSdk.ui.ForecastScreen
import org.junit.Rule
import org.junit.Test

class ForecastScreenPaparazziTest {
    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5,
        theme = "android:Theme.Material.Light.NoActionBar"
        // ...see docs for more options
    )

    @Test
    fun launchComposable() {
        paparazzi.snapshot {
            ForecastScreen(cityName = "Berlin")
        }
    }
}