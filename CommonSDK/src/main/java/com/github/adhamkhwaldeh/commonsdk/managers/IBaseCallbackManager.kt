package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener

interface IBaseCallbackManager<T> where T : ICallbackListener {

    /**
     * Add listener
     *
     * @param listener
     */
    fun addListener(listener: T)

    /**
     * Remove listener
     *
     * @param listener
     */
    fun removeListener(listener: T)
    fun clearListeners()

}