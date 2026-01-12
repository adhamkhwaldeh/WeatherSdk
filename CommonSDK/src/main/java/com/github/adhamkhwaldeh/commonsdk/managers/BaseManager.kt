package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.configs.ManagerConfigInterface
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener

/**
 * Base manager
 *
 * @constructor Create empty Base manager
 */
interface BaseManager<
    TCall : CallbackListener,
    TError : ErrorListener,
    TConfig : ManagerConfigInterface,
> :
    BaseCallbackManager<TCall>,
    BaseErrorManager<TError>,
    BaseConfigurableManager<TConfig> {
    fun isStarted(): Boolean

    fun start()

    fun stop()

    fun pause()

    fun resume()
}
