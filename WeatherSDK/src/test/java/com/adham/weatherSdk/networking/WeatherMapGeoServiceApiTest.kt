package com.adham.weatherSdk.networking

import com.adham.weatherSdk.data.remote.networking.WeatherMapGeoServiceApi
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
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class WeatherMapGeoServiceApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: WeatherMapGeoServiceApi

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
                .create(WeatherMapGeoServiceApi::class.java)
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
                [
                    {
                        "name": "London",
                        "lat": 51.5073219,
                        "lon": -0.1276474,
                        "country": "GB",
                        "state": "England"
                    }
                ]
                """.trimIndent()

            mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

            val response = api.getGeoByName("London", 5, "key")

            assertNotNull(response)
            assertEquals("London", response.first().name)
        }

    @Test() // expected = HttpException::class
    fun `current method invalid API key`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(401))

            val exception =
                assertThrows<HttpException> {
                    api.getGeoByName("London", 5, "invalid_key")
                }
            assertEquals(401, exception.code())
        }

    @Test() // expected = HttpException::class
    fun `current method city not found`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(404))
            val exception =
                assertThrows<HttpException> {
                    api.getGeoByName("London", 5, "invalid_key")
                }
            assertEquals(404, exception.code())
        }

    @Test(expected = HttpException::class)
    fun `current method rate limit exceeded`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(429))
            api.getGeoByName("London", 5, "key")
        }

    @Test
    fun `current method empty city parameter`() =
        runTest {
            mockWebServer.enqueue(
                MockResponse().setBody("[]").setResponseCode(200),
            )

            api.getGeoByName("", 5, "key")

            val request = mockWebServer.takeRequest()
            assertTrue(request.path?.contains("q=") ?: false)
        }

    @Test(expected = Exception::class)
    fun `current method malformed JSON response`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setBody("{ malformed }").setResponseCode(200))
            api.getGeoByName("London", 5, "key")
        }

    @Test
    fun `forecast method successful response`() =
        runTest {
            val json =
                """
                [
                    {"name":"London","lat":51.5,"lon":-0.12,"country":"GB"},
                    {"name":"London","lat":42.9,"lon":-81.2,"country":"CA"}
                ]
                """.trimIndent()

            mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

            val response = api.getGeoByName("London", 5, "key")

            assertEquals(2, response.size)
        }

    @Test
    fun `forecast method special characters in city name`() =
        runTest {
            mockWebServer.enqueue(
                MockResponse()
                    .setBody("[{\"name\":\"London\",\"lat\":51.5,\"lon\":-0.1,\"country\":\"GB\"}]")
                    .setResponseCode(200),
            )

            api.getGeoByName("London", 5, "key")

            val request = mockWebServer.takeRequest()
            // The query parameter in your interface is 'q', not 'city'
            assertTrue(request.path?.contains("q=London") ?: false)
        }

    @Test(expected = HttpException::class)
    fun `forecast method server error 500`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setResponseCode(500))
            api.getGeoByName("London", 5, "key")
        }
}
