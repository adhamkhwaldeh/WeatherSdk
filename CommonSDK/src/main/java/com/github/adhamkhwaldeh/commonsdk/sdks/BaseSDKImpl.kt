package com.github.adhamkhwaldeh.commonsdk.sdks

import android.content.Context
import com.github.adhamkhwaldeh.commonsdk.BaseSDKOptionBuilder
import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.configs.ManagerConfigInterface
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.logging.LoggerProxy
import com.github.adhamkhwaldeh.commonsdk.managers.BaseManager
import com.github.adhamkhwaldeh.commonsdk.models.ManagerKey
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import java.util.WeakHashMap

/**
 * Base s d k
 *
 * @param TSdkStatus
 * @param TConfig
 * @property context
 * @property sdkConfig
 * @constructor Create empty Base s d k
 */
abstract class BaseSDKImpl<
    TSdkStatus : CallbackListener,
    TError : ErrorListener,
    TConfig : BaseSDKOptions,
>(
    val context: Context,
    sdkConfig: TConfig,
    private val callbackSdk: BaseStatusSDK<TSdkStatus> = BaseStatusSDKImpl(),
    private val errorSdk: BaseGlobalErrorSDK<TError> = BaseGlobalErrorSDKImpl(),
    private val configSDK: BaseConfigSDK<TConfig> = BaseConfigSDKImpl(sdkConfig),
) : BaseSDK<TSdkStatus, TError, TConfig>,
    BaseStatusSDK<TSdkStatus> by callbackSdk,
    BaseGlobalErrorSDK<TError> by errorSdk,
    BaseConfigSDK<TConfig> by configSDK {
    protected val logger: LoggerProxy
        get() {
            return (configSDK as BaseConfigSDKImpl<TConfig>).logger
        }

    protected val behaviorManagers:
        WeakHashMap<ManagerKey, BaseManager<out CallbackListener, out ErrorListener, out ManagerConfigInterface>>
        get() {
            return (configSDK as BaseConfigSDKImpl<TConfig>).behaviorManagers
        }

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
        T : Builder<T, TSdkStatus, TError, TConfig, C>,
        TSdkStatus : CallbackListener,
        TError : ErrorListener,
        TConfig : BaseSDKOptions,
        C : BaseSDK<TSdkStatus, TError, TConfig>,
    >(
        protected val context: Context,
    ) : BaseSDKOptionBuilder<T, TSdkStatus, TError, TConfig, C> {
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
}
