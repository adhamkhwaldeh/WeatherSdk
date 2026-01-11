package com.adham.weatherSdk.helpers

import android.content.res.Resources
import androidx.annotation.RawRes

object AssetsHelper {

    fun Resources.readFromAssets(@RawRes resId: Int): String {
        return this.openRawResource(resId)
            .bufferedReader().use { it.readText() }
    }
}
