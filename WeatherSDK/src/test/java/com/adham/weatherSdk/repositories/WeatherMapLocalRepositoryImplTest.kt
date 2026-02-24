package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.data.repositories.WeatherMapLocalRepositoryImpl
import com.adham.weatherSdk.domain.preferences.SharedPrefsManager
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.helpers.ConstantsHelpers
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class WeatherMapLocalRepositoryImplTest {
    private lateinit var sharedPrefsManager: SharedPrefsManager
    private lateinit var repository: WeatherMapLocalRepository

    @Before
    fun setUp() {
        sharedPrefsManager = mockk(relaxed = true)
        repository = WeatherMapLocalRepositoryImpl(sharedPrefsManager)
    }

    @Test
    fun `saveApiKey standard persistence check`() {
        val key = "123456789"
        repository.saveApiKey(key)
        verify(exactly = 1) {
            sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, key)
        }
    }

    @Test
    fun `getApiKey retrieval consistency`() {
        val expectedKey = "1234567"

        every {
            sharedPrefsManager.getStringData(ConstantsHelpers.API_WEATHER_KEY_TAG)
        } returns expectedKey

        val actualKey = repository.getApiKey()

        assertEquals(expectedKey, actualKey)

        verify {
            sharedPrefsManager.getStringData(ConstantsHelpers.API_WEATHER_KEY_TAG)
        }
    }

    @Test
    fun `getApiKey initial empty state`() {
        every { sharedPrefsManager.getStringData(ConstantsHelpers.API_WEATHER_KEY_TAG) } returns null

        val result = repository.getApiKey()

        assertEquals("", result)
    }

    @Test
    fun `saveApiKey empty string handling`() {
        val key = ""
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, key) }

        every { sharedPrefsManager.getStringData(ConstantsHelpers.API_WEATHER_KEY_TAG) } returns null
        assertEquals("", repository.getApiKey())
    }

    @Test
    fun `saveApiKey null character or whitespace check`() {
        val key = "   "
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, key) }
    }

    @Test
    fun `saveApiKey overwrite existing key`() {
        repository.saveApiKey("first_key")
        repository.saveApiKey("second_key")

        verify { sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, "first_key") }
        verify { sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, "second_key") }
    }

    @Test
    fun `saveApiKey large string limit test`() {
        val largeKey = "a".repeat(10000)
        repository.saveApiKey(largeKey)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, largeKey) }
    }

    @Test
    fun `saveApiKey thread safety verification`() {
        val service = Executors.newFixedThreadPool(10)
        repeat(100) { i ->
            service.execute {
                repository.saveApiKey("key_$i")
            }
        }
        service.shutdown()
        service.awaitTermination(1, TimeUnit.SECONDS)

        // Verifies that 100 calls were made without crashing
        verify(exactly = 100) {
            sharedPrefsManager.save(
                ConstantsHelpers.API_WEATHER_KEY_TAG,
                any(),
            )
        }
    }

    @Test
    fun `saveApiKey special character encoding`() {
        val key = "key_!@#$%^&*()_+"
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_WEATHER_KEY_TAG, key) }
    }
}
