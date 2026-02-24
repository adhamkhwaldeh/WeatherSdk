package com.adham.weatherSample.extensions

import androidx.annotation.StringRes
import com.adham.weatherSample.domain.models.ThemeMode
import com.adham.weatherSdk.R

@get:StringRes
val ThemeMode.res: Int
    get() {
        return if (this == ThemeMode.DARK) {
            R.string.dark
        } else if (this == ThemeMode.LIGHT) {
            R.string.light
        } else {
            R.string.system
        }
    }
