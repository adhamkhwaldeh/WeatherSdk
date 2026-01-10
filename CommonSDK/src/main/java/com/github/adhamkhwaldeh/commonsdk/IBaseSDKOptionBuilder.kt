package com.github.adhamkhwaldeh.commonsdk

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface IBaseSDKOptionBuilder<T, TSdkStatus : ICallbackListener,
        TConfig : BaseSDKOptions, C : IBaseSDK<TSdkStatus, TConfig>> {

    fun setupOptions(options: TConfig): T


    fun build(): C
}