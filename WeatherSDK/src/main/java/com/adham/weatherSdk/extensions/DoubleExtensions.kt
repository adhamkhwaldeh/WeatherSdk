package com.adham.weatherSdk.extensions

import com.adham.weatherSdk.helpers.ConstantsHelpers

fun Double.kelvinToCelsius(): Double = this - ConstantsHelpers.KELVIN_TO_CELSIUS

fun Double.kelvinToCelsiusInt(): Int = (this - ConstantsHelpers.KELVIN_TO_CELSIUS).toInt()
