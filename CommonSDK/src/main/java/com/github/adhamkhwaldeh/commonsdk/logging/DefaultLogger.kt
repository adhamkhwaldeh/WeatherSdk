package com.github.adhamkhwaldeh.commonsdk.logging

import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

/**
 * The default implementation of `ILogger` which writes to Android's standard `Log` class.
 */
internal class DefaultLogger : Logger {
    override fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    ) {
        if (config.isLoggingEnabled && config.isDebugMode) {
            // we disabled the log by default
        }
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable?,
    ) {
        // we disabled the log by default
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    ) {
        // we disabled the log by default
    }

    override fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    ) {
        // we disabled the log by default
    }
}
