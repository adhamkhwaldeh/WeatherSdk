package com.adham.weatherSdk.localStorages

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class SharedPrefsManagerImplTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPrefsManager: SharedPrefsManagerImpl

    @Before
    fun setUp() {
        sharedPreferences = mockk(relaxed = true)
        editor = mockk(relaxed = true)
        every { sharedPreferences.edit() } returns editor
        sharedPrefsManager = SharedPrefsManagerImpl(sharedPreferences)
    }

    @Test
    fun `save standard string value`() {
        val key = "testKey"
        val value = "testValue"

        sharedPrefsManager.save(key, value)

        verify { editor.putString(key, value) }
        verify { editor.apply() }
    }

    @Test
    fun `save empty string value`() {
        val key = "emptyKey"
        val value = ""

        sharedPrefsManager.save(key, value)

        verify { editor.putString(key, value) }
        verify { editor.apply() }
    }

    @Test
    fun `save blank string value`() {
        val key = "blankKey"
        val value = "   "

        sharedPrefsManager.save(key, value)

        verify { editor.putString(key, value) }
        verify { editor.apply() }
    }

    @Test
    fun `save verify apply execution`() {
        sharedPrefsManager.save("key", "value")

        verify(exactly = 1) { editor.apply() }
        verify(exactly = 0) { editor.commit() }
    }

    @Test
    fun `save overwriting existing key`() {
        val key = "overwriteKey"
        sharedPrefsManager.save(key, "oldValue")
        sharedPrefsManager.save(key, "newValue")

        verify { editor.putString(key, "oldValue") }
        verify { editor.putString(key, "newValue") }
    }

    @Test
    fun `getStringData successful retrieval`() {
        val key = "retrieveKey"
        val value = "storedValue"
        every { sharedPreferences.getString(key, null) } returns value

        val result = sharedPrefsManager.getStringData(key)

        assertEquals(value, result)
    }

    @Test
    fun `getStringData missing key returns null`() {
        val key = "missingKey"
        every { sharedPreferences.getString(key, null) } returns null

        val result = sharedPrefsManager.getStringData(key)

        assertNull(result)
    }

    @Test
    fun `getStringData empty string returns null`() {
        val key = "emptyKey"
        every { sharedPreferences.getString(key, null) } returns ""

        val result = sharedPrefsManager.getStringData(key)

        assertNull(result)
    }

    @Test
    fun `getStringData whitespace string returns null`() {
        val key = "whitespaceKey"
        every { sharedPreferences.getString(key, null) } returns "   "

        val result = sharedPrefsManager.getStringData(key)

        assertNull(result)
    }

    @Test
    fun `getStringData leading trailing whitespace retention`() {
        val key = "paddedKey"
        val value = "  data  "
        every { sharedPreferences.getString(key, null) } returns value

        val result = sharedPrefsManager.getStringData(key)

        assertEquals(value, result)
    }

    @Test
    fun `getStringData large string performance`() {
        val key = "largeKey"
        val largeValue = "a".repeat(1_000_000)
        every { sharedPreferences.getString(key, null) } returns largeValue

        val result = sharedPrefsManager.getStringData(key)

        assertEquals(largeValue, result)
    }
}
