package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import java.util.concurrent.CopyOnWriteArrayList

internal class BaseCallbackManagerImpl<T : CallbackListener> : BaseCallbackManager<T> {
    internal val listeners: CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

    override fun addListener(listener: T) {
        listeners.addIfAbsent(listener)
    }

    override fun removeListener(listener: T) {
        listeners.remove(listener)
    }

    override fun clearListeners() {
        listeners.clear()
    }

    override fun notifyListeners(block: (T) -> Unit) {
        listeners.forEach { block(it) }
    }
}
