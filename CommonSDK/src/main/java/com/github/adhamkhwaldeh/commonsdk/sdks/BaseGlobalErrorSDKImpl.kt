package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import java.util.concurrent.CopyOnWriteArrayList

internal class BaseGlobalErrorSDKImpl<T : ErrorListener> : BaseGlobalErrorSDK<T> {

    //#region SDK-level Error actions
    private val globalErrorListeners = CopyOnWriteArrayList<ErrorListener>()

    /**
     * Adds a listener that will receive errors from all managers in the SDK.
     * @param listener The listener to add.
     */
    override fun addGlobalErrorListener(listener: T) {
        globalErrorListeners.addIfAbsent(listener)
    }

    /**
     * Removes a global error listener.
     * @param listener The listener to remove.
     */
    override fun removeGlobalErrorListener(listener: T) {
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

}
