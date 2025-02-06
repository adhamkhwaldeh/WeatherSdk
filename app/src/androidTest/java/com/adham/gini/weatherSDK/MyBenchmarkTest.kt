package com.adham.gini.weatherSDK

import android.content.Context
import android.content.Intent
import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MyBenchmarkTest {
    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun measureLaunchPerformance() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        benchmarkRule.measureRepeated {
            ContextCompat.startActivity(context, Intent(context, MainActivity::class.java), null)
        }
    }
}
