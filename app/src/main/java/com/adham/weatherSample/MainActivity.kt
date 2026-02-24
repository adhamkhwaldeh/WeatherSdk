package com.adham.weatherSample

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.rememberNavController
import com.adham.weatherSample.domain.models.ThemeMode
import com.adham.weatherSample.extensions.direction
import com.adham.weatherSample.extensions.findActivity
import com.adham.weatherSample.presentation.ui.AppNavHost
import com.adham.weatherSample.presentation.ui.providers.LocalWeatherThemeController
import com.adham.weatherSample.presentation.ui.providers.WeatherThemeController
import com.adham.weatherSample.presentation.ui.theme.WeatherAppTheme
import com.adham.weatherSample.presentation.viewModels.SettingsViewModel
import org.koin.compose.viewmodel.koinActivityViewModel
import java.util.Locale

val LocalAppContext =
    staticCompositionLocalOf<Context> { error("CompositionLocal LocalAppContext not present") }

/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val settingsViewModel: SettingsViewModel = koinActivityViewModel()
            val settingsUiState by settingsViewModel.uiState.collectAsState()
            val systemDarkTheme = isSystemInDarkTheme()
            val darkTheme =
                when (settingsUiState.themeMode) {
                    ThemeMode.LIGHT -> false
                    ThemeMode.DARK -> true
                    ThemeMode.SYSTEM -> systemDarkTheme
                }
            val configuration =
                remember(settingsUiState.languageMode) {
                    val locale = Locale(settingsUiState.languageMode.code)
                    Locale.setDefault(locale)
                    Configuration(resources.configuration).apply {
                        setLocale(locale)
                    }
                }

            val context = LocalContext.current
            val activity =
                remember(context) {
                    context.findActivity()
                } ?: error("Activity not found")

            val localizedContext =
                remember(configuration) {
                    activity.createConfigurationContext(configuration)
                }

            LaunchedEffect(settingsUiState.languageMode) {
                val appLocale: LocaleListCompat =
                    LocaleListCompat.forLanguageTags(
                        settingsUiState.languageMode.code,
                    )
                AppCompatDelegate.setApplicationLocales(appLocale)
            }

            WeatherAppTheme(darkTheme = darkTheme) {
                val controller = remember { WeatherThemeController() }
                CompositionLocalProvider(
                    LocalWeatherThemeController provides controller,
                    LocalConfiguration provides configuration,
                    LocalLayoutDirection provides settingsUiState.languageMode.direction,
                    LocalActivity provides activity,
                    LocalAppContext provides localizedContext,
                    LocalContext provides localizedContext,
                    LocalLifecycleOwner provides activity,
                    LocalActivityResultRegistryOwner provides activity,
                    LocalOnBackPressedDispatcherOwner provides activity,
                ) {
                    AppNavHost()
                }
            }
        }
    }

//    fun updateLocale(
//        context: Context,
//        language: LanguageMode,
//    ): Context {
//        val locale = Locale(language.code)
//        Locale.setDefault(locale)
//
//        val config = Configuration(context.resources.configuration)
//        config.setLocale(locale)
// //        AppCompatDelegate.setApplicationLocales(
// //            LocaleListCompat.forLanguageTags(language.code),
// //        )
//        val delegatedContext = context.createConfigurationContext(config)
// //        return delegatedContext.findActivity() ?: delegatedContext
//        return delegatedContext
//    }
}
