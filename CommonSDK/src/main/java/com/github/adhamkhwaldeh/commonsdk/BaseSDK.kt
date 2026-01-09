package com.github.adhamkhwaldeh.commonsdk

import android.content.Context
import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.managers.IBaseManager
import com.github.adhamkhwaldeh.commonsdk.models.ManagerKey
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import java.util.WeakHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Base s d k
 *
 * @param TConfig
 * @property context
 * @property sdkConfig
 * @constructor Create empty Base s d k
 */
abstract class BaseSDK<TConfig : BaseSDKOptions>(
    val context: Context,
    private var sdkConfig: TConfig
) : IBaseSDK<TConfig> {
    private val globalErrorListeners = CopyOnWriteArrayList<IErrorListener>()

    private val logger by lazy {
        Logger()
    }

    //#region SDK-level Actions

    // Using a WeakHashMap allows garbage collection of the Activity/View keys when they are destroyed,
    // preventing memory leaks.
    private val behaviorManagers =
        WeakHashMap<ManagerKey, IBaseManager<out ICallbackListener, out IErrorListener, out IManagerConfigInterface>>()

    /**
     * Adds a listener that will receive errors from all managers in the SDK.
     * @param listener The listener to add.
     */
    override fun addGlobalErrorListener(listener: IErrorListener) {
        globalErrorListeners.addIfAbsent(listener)
    }

    /**
     * Removes a global error listener.
     * @param listener The listener to remove.
     */
    override fun removeGlobalErrorListener(listener: IErrorListener) {
        globalErrorListeners.remove(listener)
    }

    /**
     * Clear global error listeners
     *
     */
    override fun clearGlobalErrorListeners() {
        globalErrorListeners.clear()
    }

    /**
     * Update s d k config
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
     * @param newConfig The new [UserBehaviorSDKConfig] to apply.
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

    /**
     * Update loggers
     *
     * @param loggers
     */
    override fun updateLoggers(loggers: List<ILogger>) {
        // Configure the global logger based on the builder settings.
        if (loggers.isNotEmpty()) {
            logger.clearLoggers()
            loggers.forEach { logger.addLogger(it) }
        }
    }
    //#endregion

}