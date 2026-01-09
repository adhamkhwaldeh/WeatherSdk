package com.github.adhamkhwaldeh.commonsdk

import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface IBaseSDK<TConfig : BaseSDKOptions> {

    fun addGlobalErrorListener(listener: IErrorListener)

    fun removeGlobalErrorListener(listener: IErrorListener)

    fun clearGlobalErrorListeners()

    fun updateSDKConfig(changeOptions: (TConfig) -> TConfig)

    fun updateSDKConfig(newConfig: TConfig)

    fun updateLoggers(loggers: List<ILogger>)

}