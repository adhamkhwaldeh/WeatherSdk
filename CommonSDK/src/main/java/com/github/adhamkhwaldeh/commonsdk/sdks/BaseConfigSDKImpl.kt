package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.configs.ManagerConfigInterface
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.logging.LoggerProxy
import com.github.adhamkhwaldeh.commonsdk.logging.LoggerProxyImpl
import com.github.adhamkhwaldeh.commonsdk.managers.BaseManager
import com.github.adhamkhwaldeh.commonsdk.models.ManagerKey
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import java.util.WeakHashMap
import kotlin.collections.forEach

internal class BaseConfigSDKImpl<TConfig : BaseSDKOptions>(
    internal var sdkConfig: TConfig,
) : BaseConfigSDK<TConfig> {
    // #region SDK-level Logging actions
    internal val logger: LoggerProxy by lazy {
        LoggerProxyImpl()
    }

    /**
     * Update loggers
     *
     * @param loggers
     */
    override fun updateLoggers(loggers: List<Logger>) {
        // Configure the global logger based on the builder settings.
        if (loggers.isNotEmpty()) {
            logger.clearLoggers()
            loggers.forEach { logger.addLogger(it) }
        }
    }
    // #endregion

    // #region SDK-level Config Actions
    internal val behaviorManagers =
        WeakHashMap<ManagerKey, BaseManager<out CallbackListener, out ErrorListener, out ManagerConfigInterface>>()

    // #endregion

    // #region SDK-level managers Actions

    /**
     * Update sdk config
     *
     * @param changeOptions
     * @receiver
     */
    override fun updateSDKConfig(changeOptions: (TConfig) -> TConfig) {
        updateSDKConfig(changeOptions(sdkConfig))
    }

    /**
     * Overrides the current SDK configuration and propagates the common settings
     * to all active managers. This allows for runtime changes to settings like
     * logging and debug modes.
     *
     * @param newConfig The new [TConfig] to apply.
     */
    override fun updateSDKConfig(newConfig: TConfig) {
        this.sdkConfig = newConfig

        logger.i("updateSDKConfig", "SDK config updated. Propagating to all managers.", sdkConfig)

        // Iterate through a copy of the keys to avoid concurrent modification issues
        behaviorManagers.keys.toList().forEach { key ->
            val manager = behaviorManagers[key]
            if (manager != null && manager.canOverride()) {
                manager.updateDefaultConfig(newConfig)
            }
        }
    }
    // #endregion
}
