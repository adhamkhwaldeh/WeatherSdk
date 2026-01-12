package com.adham.weatherSdk.helpers

import android.content.res.Resources
import androidx.annotation.RawRes

object AssetsHelper {
    fun Resources.readFromAssets(
        @RawRes resId: Int,
    ): String =
        this
            .openRawResource(resId)
            .bufferedReader()
            .use { it.readText() }
}
