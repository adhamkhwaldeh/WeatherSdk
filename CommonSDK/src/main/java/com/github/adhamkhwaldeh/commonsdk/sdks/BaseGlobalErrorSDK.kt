package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener

interface BaseGlobalErrorSDK<TError : ErrorListener> {
    // #region SDK-level Error actions

    fun addGlobalErrorListener(listener: TError)

    fun removeGlobalErrorListener(listener: TError)

    fun clearGlobalErrorListeners()

    fun notifyGlobalErrorListeners(error: BaseSDKException)

    // #endregion
}
