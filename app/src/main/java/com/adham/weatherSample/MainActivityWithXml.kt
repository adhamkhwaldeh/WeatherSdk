package com.adham.weatherSample

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.adham.weatherSample.ui.EnterCityScreenFragment
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import com.adham.weatherSample.ui.ForecastScreenFragment
import com.adham.weatherSdk.WeatherSDK
import org.koin.android.ext.android.inject

/**
 * Main activity with xml
 *
 * @constructor Create empty Main activity with xml
 */
class MainActivityWithXml : FragmentActivity() {

    val weatherSDK: WeatherSDK by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_with_xml_layout)
        replace(EnterCityScreenFragment(), EnterCityScreenFragment::class.java.name)

        weatherSDK.sdkStatus.observe(this) {
            if (it is WeatherSdkStatus.OnFinish) {
                replace(EnterCityScreenFragment(), EnterCityScreenFragment::class.java.name)
            } else if (it is WeatherSdkStatus.OnLaunchForecast) {
                replace(
                    ForecastScreenFragment.newInstance(it.cityName),
                    ForecastScreenFragment::class.java.name
                )
            }
        }

    }

    private fun replace(fragment: Fragment, tag: String) {
        val ft = supportFragmentManager.beginTransaction();
        ft.replace(R.id.container, fragment, tag)
        ft.addToBackStack(null)
        ft.commit()
    }

}