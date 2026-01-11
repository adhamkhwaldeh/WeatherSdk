package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import java.util.concurrent.CopyOnWriteArrayList

class BaseStatusSDKImpl<TSdkStatus : CallbackListener> : BaseStatusSDK<TSdkStatus> {

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
}
