package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.data.repositories.WeatherLocalRepositoryImpl
import com.adham.weatherSdk.helpers.ConstantsHelpers
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class WeatherLocalRepositoryImplTest {
    private lateinit var sharedPrefsManager: SharedPrefsManager
    private lateinit var repository: WeatherLocalRepositoryImpl

    @Before
    fun setUp() {
        sharedPrefsManager = mockk(relaxed = true)
        repository = WeatherLocalRepositoryImpl(sharedPrefsManager)
    }

    @Test
    fun `saveApiKey standard persistence check`() {
        val key = "abc123456"
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, key) }
    }

    @Test
    fun `getApiKey retrieval consistency`() {
        val expectedKey = "test_api_key"
        every { sharedPrefsManager.getStringData(ConstantsHelpers.API_KEY_TAG) } returns expectedKey

        val actualKey = repository.getApiKey()

        assertEquals(expectedKey, actualKey)
    }

    @Test
    fun `getApiKey initial empty state`() {
        every { sharedPrefsManager.getStringData(ConstantsHelpers.API_KEY_TAG) } returns null

        val result = repository.getApiKey()

        assertEquals("", result)
    }

    @Test
    fun `saveApiKey empty string handling`() {
        val key = ""
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, key) }

        every { sharedPrefsManager.getStringData(ConstantsHelpers.API_KEY_TAG) } returns null
        assertEquals("", repository.getApiKey())
    }

    @Test
    fun `saveApiKey null character or whitespace check`() {
        val key = "   "
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, key) }
    }

    @Test
    fun `saveApiKey overwrite existing key`() {
        repository.saveApiKey("first_key")
        repository.saveApiKey("second_key")

        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, "first_key") }
        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, "second_key") }
    }

    @Test
    fun `saveApiKey large string limit test`() {
        val largeKey = "a".repeat(10000)
        repository.saveApiKey(largeKey)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, largeKey) }
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
        verify(exactly = 100) { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, any()) }
    }

    @Test
    fun `saveApiKey special character encoding`() {
        val key = "key_!@#$%^&*()_+"
        repository.saveApiKey(key)
        verify { sharedPrefsManager.save(ConstantsHelpers.API_KEY_TAG, key) }
    }

//    @Test
//    fun `saveApiKey exception handling`() {
//        every { sharedPrefsManager.save(any(), any()) } throws RuntimeException("Storage Error")
//
//        // Should not throw exception due to try-catch in implementation
//        repository.saveApiKey("test_key")
//
//        verify { sharedPrefsManager.save(any(), any()) }
//    }
}
