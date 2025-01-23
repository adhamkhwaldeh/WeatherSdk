package com.adham.gini.weatherSDK

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.adham.gini.weatherSDK.ui.EnterCityScreenFragment
import com.adham.gini.weatherginisdk.WeatherGiniSDKBuilder
import com.adham.gini.weatherginisdk.data.states.WeatherSdkStatus
import com.adham.gini.weatherginisdk.ui.ForecastScreenFragment
import com.adham.gini.weatherginisdk.ui.navigations.NavigationItem
import kotlin.math.log

class MainActivityWithXml : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_with_xml_layout)
        replace(EnterCityScreenFragment(), EnterCityScreenFragment::class.java.name)

        WeatherGiniSDKBuilder.sdkStatus.observe(this) {
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