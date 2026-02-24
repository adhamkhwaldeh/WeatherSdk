package com.adham.weatherSample.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.adham.weatherSample.R
import com.adham.weatherSample.extensions.getColor
import com.adham.weatherSample.extensions.getDayOfWeek
import com.adham.weatherSample.extensions.getIcon
import com.adham.weatherSample.presentation.ui.providers.LocalWeatherThemeController
import com.adham.weatherSdk.domain.models.ForecastWeatherMapDailyModel

/**
 * Hourly forecast item
 *
 * @param item
 */
@Suppress("LongMethod")
// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@Composable
fun WeatherForecastItem(
    item: ForecastWeatherMapDailyModel,
    modifier: Modifier = Modifier,
) {
    val weatherThemeController = LocalWeatherThemeController.current

    val textPadding = PaddingValues(4.dp)
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(
                    colorResource(
                        weatherThemeController
                            .weatherTheme
                            .getColor(),
                    ),
                ),
        shape = RoundedCornerShape(0.0.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    colorResource(
                        weatherThemeController
                            .weatherTheme
                            .getColor(),
                    ),
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
        ) {
            Text(
                text = item.dtTxt.getDayOfWeek(),
                style =
                    MaterialTheme.typography.bodyMedium
                        .copy(fontWeight = FontWeight.SemiBold, color = Color.White),
                modifier =
                    Modifier
                        .weight(1.0f)
                        .padding(textPadding),
            )
            Icon(
                painter = painterResource(id = item.weather.weatherMain.getIcon()),
                contentDescription = "Custom icon",
                modifier =
                    Modifier
                        .weight(1.0f)
                        .padding(textPadding),
                tint = Color.White,
            )
            Text(
                text =
                    item.temp.toString() +
                        stringResource(
                            R.string.celsius,
                        ),
                style =
                    MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    ),
                modifier =
                    Modifier
                        .weight(1.0f)
                        .padding(textPadding),
                textAlign = TextAlign.Center,
            )
        }
    }
}
