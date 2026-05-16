package com.adham.weatherSample.benchmark

import androidx.benchmark.junit4.BenchmarkRule
import androidx.benchmark.junit4.measureRepeated
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.weatherSample.extensions.getDayOfWeek
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringExtensionsBenchmark {

    @get:Rule
    val benchmarkRule = BenchmarkRule()

    @Test
    fun getDayOfWeekBenchmark() {
        val input = "2026-05-16"
        benchmarkRule.measureRepeated {
            input.getDayOfWeek()
        }
    }
}
