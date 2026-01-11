package com.adham.weatherSample.logging

import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

class ConsoleLogger : ILogger {
    override fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }

    override fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable?
    ) {

    }

    override fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }

}