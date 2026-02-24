package com.adham.weatherSample.presentation.ui.components.settings

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import com.adham.weatherSample.domain.models.SettingsParamsModel

@Composable
fun SettingsSwitchSelector(
    params: SettingsParamsModel,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    SettingsCard(
        params = params,
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}
