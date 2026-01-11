package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface BaseSDK<TSdkStatus : CallbackListener,
        TError : ErrorListener,
        TConfig : BaseSDKOptions> :
    BaseStatusSDK<TSdkStatus>, BaseGlobalErrorSDK<TError>, BaseConfigSDK<TConfig>
