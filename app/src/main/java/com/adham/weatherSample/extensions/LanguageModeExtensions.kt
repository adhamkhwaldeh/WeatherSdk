package com.adham.weatherSample.extensions

import androidx.annotation.StringRes
import androidx.compose.ui.unit.LayoutDirection
import com.adham.weatherSample.R
import com.adham.weatherSample.domain.models.LanguageMode

val LanguageMode.direction: LayoutDirection
    get() {
        return if (this == LanguageMode.ARABIC) {
            LayoutDirection.Rtl
        } else {
            LayoutDirection.Ltr
        }
    }

@get:StringRes
val LanguageMode.res: Int
    get() {
        return if (this == LanguageMode.ARABIC) {
            R.string.Arabic
        } else {
            R.string.English
        }
    }
