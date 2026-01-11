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

    /**
     * Clear listeners
     *
     */
    fun clearListeners()

    /**
     * Notify listeners
     *
     * @param block The action to perform for each listener.
     */
    fun notifyListeners(block: (T) -> Unit)

}
