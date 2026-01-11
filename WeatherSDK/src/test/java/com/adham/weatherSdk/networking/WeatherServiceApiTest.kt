package com.adham.weatherSdk.networking

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherServiceApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: WeatherServiceApi

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(WeatherServiceApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `current method successful response`() = runTest {
        val json = """
            {
                "data": [
                    {
                        "city_name": "London",
                        "temp": 15.5,
                        "weather": { "description": "Cloudy", "icon": "c01d" }
                    }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = api.current("London", "key")

        assertNotNull(response)
        assertEquals("London", response.data[0].city_name)
        assertEquals(15.5, response.data[0].temp, 0.1)
    }

    @Test(expected = HttpException::class)
    fun `current method invalid API key`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(401))
        api.current("London", "invalid_key")
    }

    @Test(expected = HttpException::class)
    fun `current method city not found`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
        api.current("InvalidCity", "key")
    }

    @Test(expected = HttpException::class)
    fun `current method rate limit exceeded`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(429))
        api.current("London", "key")
    }

    @Test
    fun `current method empty city parameter`() = runTest {
        // Enqueue success because the interface doesn't validate, only the API would.
        // We verify the URL formed.
        mockWebServer.enqueue(MockResponse().setBody("{\"data\":[]}").setResponseCode(200))

        api.current("", "key")

        val request = mockWebServer.takeRequest()
        assertTrue(request.path?.contains("city=") ?: false)
    }

    @Test(expected = Exception::class)
    fun `current method malformed JSON response`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("{ malformed }").setResponseCode(200))
        api.current("London", "key")
    }

    @Test
    fun `forecast method successful response`() = runTest {
        val json = """
            {
                "city_name": "London",
                "data": [
                    { "temp": 10.0, "timestamp_local": "2023-01-01T00:00:00" },
                    { "temp": 11.0, "timestamp_local": "2023-01-01T01:00:00" }
                ]
            }
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val response = api.forecast("London", 2, "key")

        assertEquals(2, response.data.size)
//        assertEquals("London", response.cityName)
    }

    @Test
    fun `forecast method special characters in city name`() = runTest {
        mockWebServer.enqueue(MockResponse().setBody("{\"data\":[]}").setResponseCode(200))

        api.forecast("New York", 24, "key")

        val request = mockWebServer.takeRequest()
        // Check if encoded correctly
        assertTrue(request.path?.contains("city=New%20York") ?: false)
    }

    @Test(expected = HttpException::class)
    fun `forecast method server error 500`() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        api.forecast("London", 24, "key")
    }
}
