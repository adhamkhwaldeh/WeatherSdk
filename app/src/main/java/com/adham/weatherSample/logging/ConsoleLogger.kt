package com.adham.weatherSample.logging

import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

class ConsoleLogger : Logger {
    override fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    ) {
        // Implement timber or similar library
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable?,
    ) {
        // Implement timber or similar library
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    ) {
        // Implement timber or similar library
    }

    override fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    ) {
        // Implement timber or similar library
    }
}
