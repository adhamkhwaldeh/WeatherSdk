package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener

interface BaseErrorManager<T> where T : ErrorListener {
    /**
     * Add error listener
     *
     * @param listener
     */
    fun addErrorListener(listener: T)

    /**
     * Remove error listener
     *
     * @param listener
     */
    fun removeErrorListener(listener: T)

    /**
     * Remove all error listeners.
     */
    fun clearErrorListeners()

    fun notifyErrorListeners(error: BaseSDKException)
}
