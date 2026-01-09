package com.github.adhamkhwaldeh.commonsdk.logging

import android.util.Log
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

/**
 * The default implementation of `ILogger` which writes to Android's standard `Log` class.
 */
internal class DefaultLogger : ILogger {
    override fun d(tag: String, message: String, config: BaseSDKOptions) {
        if (config.isLoggingEnabled && config.isDebugMode) {
            Log.d(tag, message)
        }
    }

    override fun e(tag: String, message: String, config: BaseSDKOptions, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun w(tag: String, message: String, config: BaseSDKOptions) {
        Log.w(tag, message)
    }

    override fun i(tag: String, message: String, config: BaseSDKOptions) {
        Log.i(tag, message)
    }
}