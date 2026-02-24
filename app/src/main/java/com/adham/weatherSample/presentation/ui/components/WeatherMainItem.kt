package com.adham.weatherSample.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.adham.weatherSample.R
import com.adham.weatherSdk.extensions.kelvinToCelsiusInt

@Composable
fun WeatherMainItem(
    modifier: Modifier,
    temp: Double,
    label: String,
) {
    Text(
        modifier = modifier,
        text = "${temp.kelvinToCelsiusInt()}${stringResource(R.string.celsiusSign)}\n$label",
        style = MaterialTheme.typography.labelLarge.copy(color = Color.White),
        textAlign = TextAlign.Center,
    )
}
