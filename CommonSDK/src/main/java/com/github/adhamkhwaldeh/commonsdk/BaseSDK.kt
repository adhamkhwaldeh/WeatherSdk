package com.github.adhamkhwaldeh.commonsdk

import android.content.Context
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
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
 * @param TSdkStatus
 * @param TConfig
 * @property context
 * @property sdkConfig
 * @constructor Create empty Base s d k
 */
abstract class BaseSDK<TSdkStatus : ICallbackListener,
        TConfig : BaseSDKOptions>(
    val context: Context,
    protected var sdkConfig: TConfig
) : IBaseSDK<TSdkStatus, TConfig> {

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
            TSdkStatus : ICallbackListener,
            TConfig : BaseSDKOptions,
            C : IBaseSDK<TSdkStatus, TConfig>
            >(protected val context: Context) : IBaseSDKOptionBuilder<T, TSdkStatus, TConfig, C> {

        protected var sdkConfig: TConfig? = null
        protected val customLoggers = mutableListOf<ILogger>()

        @Suppress("UNCHECKED_CAST")
        override fun setupOptions(options: TConfig): T {
            this.sdkConfig = options
            return this as T
        }

        @Suppress("UNCHECKED_CAST")
        fun addLogger(logger: ILogger): T {
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
    private val globalErrorListeners = CopyOnWriteArrayList<IErrorListener>()

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

    override fun notifyGlobalErrorListeners(error: BaseSDKException) {
        for (listener in globalErrorListeners) {
            listener.onError(error)
        }
    }

    //#endregion

    //#region SDK-level Logging actions
    protected val logger by lazy {
        Logger()
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

    //#region SDK-level Config Actions
    protected val behaviorManagers =
        WeakHashMap<ManagerKey, IBaseManager<out ICallbackListener, out IErrorListener, out IManagerConfigInterface>>()

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