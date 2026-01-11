package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import java.util.concurrent.CopyOnWriteArrayList

internal class BaseErrorManagerImpl<T : ErrorListener> : BaseErrorManager<T> {
    internal val errorListeners: CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

    override fun addErrorListener(listener: T) {
        errorListeners.addIfAbsent(listener)
    }

    override fun removeErrorListener(listener: T) {
        errorListeners.remove(listener)
    }

    override fun clearErrorListeners() {
        errorListeners.clear()
    }

    override fun notifyErrorListeners(error: BaseSDKException) {
        for (listener in errorListeners) {
            listener.onError(error)
        }
    }
}
