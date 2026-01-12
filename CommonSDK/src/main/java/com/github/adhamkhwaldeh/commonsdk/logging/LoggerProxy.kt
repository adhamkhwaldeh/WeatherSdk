package com.github.adhamkhwaldeh.commonsdk.logging

interface LoggerProxy : Logger {
    fun setLoggers(newLoggers: List<Logger>)

    fun addLogger(newLogger: Logger)

    fun removeLogger(logger: Logger)

    fun clearLoggers()
}
