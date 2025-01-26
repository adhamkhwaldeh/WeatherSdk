package com.adham.gini.weatherginisdk.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.adham.gini.weatherginisdk.R
import com.adham.gini.weatherginisdk.data.dtos.HourlyForecastModel
import com.adham.gini.weatherginisdk.helpers.DateHelpers


/**
 * Hourly forecast item
 *
 * @param item
 */
@Composable
fun HourlyForecastItem(item: HourlyForecastModel) {
    val textPadding = PaddingValues(4.dp)
    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(0.0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
//                .background(MaterialTheme.colorScheme.surface),
        ) {
            Text(
                text = DateHelpers.getHoursOnlyFromStanderFormat(item.timestamp_local) ,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(textPadding)
            )
            Text(
                text = "${item.temp}${stringResource(R.string.celsius)}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(textPadding)
                    .padding(
                        end = 4.dp
                    )
            )
            Text(
                text = item.weather.description,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(textPadding)
            )
        }
        HorizontalDivider()
    }

}