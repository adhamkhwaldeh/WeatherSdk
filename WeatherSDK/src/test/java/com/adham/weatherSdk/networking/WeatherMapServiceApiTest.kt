package com.adham.weatherSdk.networking

import com.adham.weatherSdk.data.remote.networking.WeatherMapServiceApi
import com.adham.weatherSdk.domain.models.AddressModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherMapServiceApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: WeatherMapServiceApi

    private val address = AddressModel(name = "London", lat = "", lon = "")

    private val moshi =
        Moshi
            .Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        api =
            Retrofit
                .Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(WeatherMapServiceApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `current method successful response`() =
        runTest {
            val json =
                """
                {
                    "weather": [
                        {
                            "id": 1,
                            "main": "Sunny",
                            "description": "London",
                            "icon":"London"
                        }
                    ],
                    "main":{
                        "temp": 2.0,
                        "temp_min":0.0,
                        "temp_max":0.0,
                        "pressure":0,
                        "humidity":0
                    },
                   "sys":{},
                   "timezone":0,
                   "id":0,
                   "name":"London"
                }
                """.trimIndent()

            mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

            val response = api.current(address.lat, address.lon, key = "api_key")

            assertNotNull(response)
            assertEquals("London", response.name, address.name)
            assertEquals(2.0, response.main.temp, 0.1)
        }

    @Test(expected = HttpException::class)
    fun `current method invalid API key`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(401))
            api.current(address.lat, address.lon, "invalid_key")
        }

    @Test(expected = HttpException::class)
    fun `current method city not found`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(404))
            api.current(address.lat, address.lon, "key")
        }

    @Test(expected = HttpException::class)
    fun `current method rate limit exceeded`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(429))
            api.current(address.lat, address.lon, "key")
        }

    @Test(expected = Exception::class)
    fun `current method malformed JSON response`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setBody("{ malformed }").setResponseCode(200))
            api.current(address.lat, address.lon, "key")
        }

    @Test(expected = HttpException::class)
    fun `forecast method server error 500`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(500))
            api.forecast(address.lat, address.lon, "key")
        }
}
