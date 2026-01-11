package com.github.adhamkhwaldeh.commonsdk

import android.content.Context
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
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
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Base s d k
 *
 * @param TSdkStatus
 * @param TConfig
 * @property context
 * @property sdkConfig
 * @constructor Create empty Base s d k
 */
abstract class BaseSDKImpl<TSdkStatus : CallbackListener,
        TConfig : BaseSDKOptions>(
    val context: Context,
    protected var sdkConfig: TConfig
) : BaseSDK<TSdkStatus, TConfig> {

    /**
     * Generic Builder for the SDK.
     *
     * @param T The concrete builder type for chaining.
     * @param TSdkStatus The type of SDK status listener.
     * @param TConfig The type of SDK options.
     * @param C The type of SDK being built.
     * @property context The application context.
     */
    abstract class Builder<
            T : Builder<T, TSdkStatus, TConfig, C>,
            TSdkStatus : CallbackListener,
            TConfig : BaseSDKOptions,
            C : BaseSDK<TSdkStatus, TConfig>
            >(protected val context: Context) : BaseSDKOptionBuilder<T, TSdkStatus, TConfig, C> {

        protected var sdkConfig: TConfig? = null
        protected val customLoggers = mutableListOf<Logger>()

        @Suppress("UNCHECKED_CAST")
        override fun setupOptions(options: TConfig): T {
            this.sdkConfig = options
            return this as T
        }

        @Suppress("UNCHECKED_CAST")
        fun addLogger(logger: Logger): T {
            customLoggers.add(logger)
            return this as T
        }
    }

    //#region SDK-level Status actions
    private val globalStatusListeners = CopyOnWriteArrayList<TSdkStatus>()

    override fun addGlobalStatusListener(listener: TSdkStatus) {
        globalStatusListeners.addIfAbsent(listener)
    }

    override fun removeGlobalStatusListener(listener: TSdkStatus) {
        globalStatusListeners.remove(listener)
    }

    override fun clearGlobalStatusListeners() {
        globalStatusListeners.clear()
    }

    override fun notifyListeners(block: (TSdkStatus) -> Unit) {
        globalStatusListeners.forEach { block(it) }
    }
    //#endregion

    //#region SDK-level Error actions
    private val globalErrorListeners = CopyOnWriteArrayList<ErrorListener>()

    /**
     * Adds a listener that will receive errors from all managers in the SDK.
     * @param listener The listener to add.
     */
    override fun addGlobalErrorListener(listener: ErrorListener) {
        globalErrorListeners.addIfAbsent(listener)
    }

    /**
     * Removes a global error listener.
     * @param listener The listener to remove.
     */
    override fun removeGlobalErrorListener(listener: ErrorListener) {
        globalErrorListeners.remove(listener)
    }

    /**
     * Clear global error listeners
     *
     */
    override fun clearGlobalErrorListeners() {
        globalErrorListeners.clear()
    }

    override fun notifyGlobalErrorListeners(error: BaseSDKException) {
        for (listener in globalErrorListeners) {
            listener.onError(error)
        }
    }

    //#endregion

    //#region SDK-level Logging actions
    protected val logger: LoggerProxy by lazy {
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
    //#endregion

    //#region SDK-level Config Actions
    protected val behaviorManagers =
        WeakHashMap<ManagerKey, BaseManager<out CallbackListener, out ErrorListener, out ManagerConfigInterface>>()

    //#endregion

    //#region SDK-level managers Actions

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
    //#endregion

}