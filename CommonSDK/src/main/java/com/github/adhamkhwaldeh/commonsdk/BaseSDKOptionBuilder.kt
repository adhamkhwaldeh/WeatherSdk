package com.github.adhamkhwaldeh.commonsdk

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface BaseSDKOptionBuilder<T, TSdkStatus : CallbackListener,
        TConfig : BaseSDKOptions, C : BaseSDK<TSdkStatus, TConfig>> {

    fun setupOptions(options: TConfig): T

    fun build(): C
}