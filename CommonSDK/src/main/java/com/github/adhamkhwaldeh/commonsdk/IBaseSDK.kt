package com.github.adhamkhwaldeh.commonsdk

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface IBaseSDK<TSdkStatus : ICallbackListener, TConfig : BaseSDKOptions> {

    //#region SDK-level Status actions

    fun addGlobalStatusListener(listener: TSdkStatus)

    fun removeGlobalStatusListener(listener: TSdkStatus)

    fun clearGlobalStatusListeners()

    fun notifyListeners(block: (TSdkStatus) -> Unit)
    //#endregion

    //#region SDK-level Error actions

    fun addGlobalErrorListener(listener: IErrorListener)

    fun removeGlobalErrorListener(listener: IErrorListener)

    fun clearGlobalErrorListeners()

    fun notifyGlobalErrorListeners(error: BaseSDKException)

    //#endregion

    //#region SDK-level Configuration actions
    fun updateSDKConfig(changeOptions: (TConfig) -> TConfig)

    fun updateSDKConfig(newConfig: TConfig)
    //#endregion

    //#region SDK-level Logging actions
    fun updateLoggers(loggers: List<ILogger>)
    //#endregion

}