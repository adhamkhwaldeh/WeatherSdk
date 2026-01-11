package com.github.adhamkhwaldeh.commonsdk

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface BaseSDK<TSdkStatus : CallbackListener, TConfig : BaseSDKOptions> {

    //#region SDK-level Status actions

    fun addGlobalStatusListener(listener: TSdkStatus)

    fun removeGlobalStatusListener(listener: TSdkStatus)

    fun clearGlobalStatusListeners()

    fun notifyListeners(block: (TSdkStatus) -> Unit)
    //#endregion

    //#region SDK-level Error actions

    fun addGlobalErrorListener(listener: ErrorListener)

    fun removeGlobalErrorListener(listener: ErrorListener)

    fun clearGlobalErrorListeners()

    fun notifyGlobalErrorListeners(error: BaseSDKException)

    //#endregion

    //#region SDK-level Configuration actions
    fun updateSDKConfig(changeOptions: (TConfig) -> TConfig)

    fun updateSDKConfig(newConfig: TConfig)
    //#endregion

    //#region SDK-level Logging actions
    fun updateLoggers(loggers: List<Logger>)
    //#endregion

}