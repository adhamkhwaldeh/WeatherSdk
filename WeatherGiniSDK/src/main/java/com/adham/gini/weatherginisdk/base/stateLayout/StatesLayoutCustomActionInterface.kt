package com.adham.gini.weatherginisdk.base.stateLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.UiComposable

interface StatesLayoutCustomActionInterface {

    fun retry(): (() -> Unit?)? = null
}