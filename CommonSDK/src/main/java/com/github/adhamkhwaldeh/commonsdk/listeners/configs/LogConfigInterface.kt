package com.github.adhamkhwaldeh.commonsdk.listeners.configs

import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel

interface LogConfigInterface {
    var isLoggingEnabled: Boolean

    var overridable: Boolean

    var logLevel: LogLevel
}
