package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener

/**
 * Base manager
 *
 * @constructor Create empty Base manager
 */
interface IBaseManager<TCall : ICallbackListener, TError : IErrorListener,
        TConfig : IManagerConfigInterface> :
    IBaseCallbackManager<TCall>, IBaseErrorManager<TError>,
    IBaseConfigurableManager<TConfig> {

    fun isStarted(): Boolean
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
