package com.adham.weatherSample.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.adham.weatherSample.LocalAppContext
import com.adham.weatherSample.R
import com.adham.weatherSample.domain.models.LanguageMode
import com.adham.weatherSample.domain.models.SettingsParamsModel
import com.adham.weatherSample.domain.models.ThemeMode
import com.adham.weatherSample.domain.models.WeatherNotificationMode
import com.adham.weatherSample.extensions.res
import com.adham.weatherSample.presentation.ui.components.settings.SettingsClickableSelector
import com.adham.weatherSample.presentation.ui.components.settings.SettingsDialogSelector
import com.adham.weatherSample.presentation.ui.components.settings.SettingsSwitchSelector
import com.adham.weatherSample.presentation.viewModels.SettingsViewModel
import org.koin.compose.viewmodel.koinActivityViewModel

// I added a suppression temporarily in order to focus on implementing the unit tests properly.
@Suppress("LongMethod")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    settingViewModel: SettingsViewModel = koinActivityViewModel(),
    showSheet: Boolean,
    onDismiss: () -> Unit,
    navController: NavHostController,
) {
    if (!showSheet) return

    val uiState by settingViewModel.uiState.collectAsState()

    val context = LocalAppContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState =
            rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
            ),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(500.dp),
        ) {
            Column(
                modifier = Modifier.matchParentSize(),
            ) {
                SettingsDialogSelector(
                    params =
                        SettingsParamsModel(
                            title = context.getString(R.string.themeModel),
                            subtitle = context.getString(R.string.choosePreferredTheme),
                            icon = Icons.Default.Style,
                        ),
                    currentItem = uiState.themeMode,
                    itemToString = {
                        Text(
                            text = context.getString(it.res),
                        )
                    },
                    items = ThemeMode.entries,
                    onItemSelected = {
                        settingViewModel.updateThemeMode(it)
                    },
                )

                SettingsDialogSelector(
                    params =
                        SettingsParamsModel(
                            title = context.getString(R.string.language),
                            subtitle = context.getString(R.string.choosePreferredLanguage),
                            icon = Icons.Default.Language,
                        ),
                    currentItem = uiState.languageMode,
                    itemToString = {
                        Text(
                            text = context.getString(it.res),
                        )
                    },
                    items = LanguageMode.entries,
                    onItemSelected = {
                        settingViewModel.updateLanguageMode(it)
                    },
                )

                SettingsSwitchSelector(
                    params =
                        SettingsParamsModel(
                            title = context.getString(R.string.notifications),
                            subtitle = context.getString(R.string.ReceiveWeatherNotifications),
                            icon = Icons.Default.Notifications,
                        ),
                    checked = uiState.weatherNotificationMode != WeatherNotificationMode.NEVER,
                    onCheckedChange = { enabled ->
                        settingViewModel.updateNotificationMode(
                            if (enabled) {
                                WeatherNotificationMode.DAILY
                            } else {
                                WeatherNotificationMode.NEVER
                            },
                        )
                    },
                )
                if (uiState.weatherNotificationMode != WeatherNotificationMode.NEVER) {
                    SettingsDialogSelector(
                        params =
                            SettingsParamsModel(
                                title = context.getString(R.string.frequentUpdate),
                                subtitle = context.getString(R.string.chooseYourPreferredUpdateTime),
                                icon = Icons.Default.Notifications,
                            ),
                        currentItem = uiState.weatherNotificationMode,
                        itemToString = {
                            Text(
                                text = context.getString(it.res),
                            )
                        },
                        items = WeatherNotificationMode.entries,
                        onItemSelected = {
                            settingViewModel.updateNotificationMode(it)
                        },
                    )
                }

                SettingsClickableSelector(
                    params =
                        SettingsParamsModel(
                            title = context.getString(R.string.selectLocation),
                            subtitle = context.getString(R.string.selectOrAddNewLocation),
                            icon = Icons.Default.LocationCity,
                        ),
                    onClick = {
                        onDismiss()
                        navController.popBackStack()
                    },
                )
            }
        }

        Button(
            onClick = {
                onDismiss()
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            Text(context.getString(R.string.Close))
        }
    }
}
