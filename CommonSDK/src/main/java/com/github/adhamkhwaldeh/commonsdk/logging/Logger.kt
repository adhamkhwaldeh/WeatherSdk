package com.github.adhamkhwaldeh.commonsdk.logging

import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

/**
 * Defines a contract for a logging utility. This allows the default Android Logcat implementation
 * to be replaced by a custom logging library (e.g., Timber, a remote logger) at runtime.
 */
interface Logger {
    fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    )

    fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable? = null,
    )

    fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    )

    fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions,
    )
}
