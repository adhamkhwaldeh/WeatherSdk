package com.github.adhamkhwaldeh.commonsdk

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import com.github.adhamkhwaldeh.commonsdk.sdks.BaseSDK

interface BaseSDKOptionBuilder<
    T,
    TSdkStatus : CallbackListener,
    TError : ErrorListener,
    TConfig : BaseSDKOptions,
    C : BaseSDK<TSdkStatus, TError, TConfig>,
> {
    fun setupOptions(options: TConfig): T

    fun build(): C
}
