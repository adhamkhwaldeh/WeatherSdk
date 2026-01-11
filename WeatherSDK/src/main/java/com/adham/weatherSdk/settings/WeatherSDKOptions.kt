package com.adham.weatherSdk.settings

import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

class WeatherSDKOptions private constructor(
    isEnabled: Boolean,
    isDebugMode: Boolean,
    isLoggingEnabled: Boolean,
    overridable: Boolean,
    logLevel: LogLevel,
    val apiKey: String
) : BaseSDKOptions(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {


    /**
     * The Builder for creating UserBehaviorSDKConfig instances.
     */
    class Builder(private val apiKey: String) : BaseBuilder<Builder, WeatherSDKOptions>() {
        /**
         * Creates the final UserBehaviorSDKConfig object.
         */
        override fun build(): WeatherSDKOptions {
            return WeatherSDKOptions(
                apiKey = apiKey,
                isEnabled = isEnabled,
                isDebugMode = isDebugMode,
                isLoggingEnabled = isLoggingEnabled,
                logLevel = logLevel,
                overridable = overridable,
            )
        }
    }
}
