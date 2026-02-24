package com.adham.weatherSdk.domain.models

import com.adham.weatherSdk.extensions.kelvinToCelsius
import kotlin.math.roundToInt

data class ForecastWeatherMapResponseModel(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ForecastWeatherMapModel>,
    val city: CityWeatherMapModel,
) {
    val days: List<ForecastWeatherMapDailyModel>
        get() {

            return list.groupBy { it.dtTxt.split(' ').first() }.map {

                val temp =
                    (
                        it.value.sumOf { innerIt ->
                            innerIt.main.temp.kelvinToCelsius()
                        } / it.value.size
                    ).roundToInt()

                val weather: WeatherMapModel =
                    it.value
                        .first()
                        .weather
                        .first()

                ForecastWeatherMapDailyModel(
                    dtTxt = it.key,
                    temp = temp,
                    weather = weather,
                )
            }
        }
}
