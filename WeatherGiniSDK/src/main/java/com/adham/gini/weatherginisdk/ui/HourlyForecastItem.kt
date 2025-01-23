package com.adham.gini.weatherginisdk.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherModel
import com.adham.gini.weatherginisdk.data.dtos.HourlyForecastModel
import com.adham.gini.weatherginisdk.helpers.DateHelpers
import java.util.Date


@Composable
fun HourlyForecastItem(item: HourlyForecastModel) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = DateHelpers.getHoursOnlyFromStanderFormat(item.timestamp_local)+"  ",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${item.temp}°C",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "${item.weather.description}°C",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}