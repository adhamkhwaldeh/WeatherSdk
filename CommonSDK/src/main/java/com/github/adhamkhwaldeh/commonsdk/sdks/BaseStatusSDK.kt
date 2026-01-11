package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener

interface BaseStatusSDK<TSdkStatus : CallbackListener> {

    //#region SDK-level Status actions

    fun addGlobalStatusListener(listener: TSdkStatus)

    fun removeGlobalStatusListener(listener: TSdkStatus)

    fun clearGlobalStatusListeners()

    fun notifyListeners(block: (TSdkStatus) -> Unit)
    //#endregion

}
