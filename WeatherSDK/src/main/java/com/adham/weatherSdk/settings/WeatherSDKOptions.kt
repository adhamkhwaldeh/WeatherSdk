package com.adham.weatherSdk.settings

import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

class WeatherSDKOptions private constructor(
    isEnabled: Boolean,
    isDebugMode: Boolean,
    isLoggingEnabled: Boolean,
    overridable: Boolean,
    logLevel: LogLevel,
) : BaseSDKOptions(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {
    internal var apiKey: String = ""
    internal var weatherApiKey: String = ""

    internal var placeApiKey: String = ""

    /**
     * The Builder for creating UserBehaviorSDKConfig instances.
     */
    class Builder(
        private val apiKey: String,
        private val weatherApiKey: String,
        private val placeApiKey: String,
    ) : BaseBuilder<Builder, WeatherSDKOptions>() {
        /**
         * Creates the final UserBehaviorSDKConfig object.
         */
        override fun build(): WeatherSDKOptions {
            val options =
                WeatherSDKOptions(
                    isEnabled = isEnabled,
                    isDebugMode = isDebugMode,
                    isLoggingEnabled = isLoggingEnabled,
                    logLevel = logLevel,
                    overridable = overridable,
                )
            options.apiKey = apiKey
            options.weatherApiKey = weatherApiKey
            options.placeApiKey = placeApiKey
            return options
        }
    }
}
