package com.adham.weatherSample.presentation.ui.components.settings

import androidx.compose.runtime.Composable
import com.adham.weatherSample.domain.models.SettingsParamsModel

@Composable
fun SettingsClickableSelector(
    params: SettingsParamsModel,
    onClick: () -> Unit,
) {
    SettingsCard(
        params = params,
        onClick = onClick,
    ) {
    }
}
