package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener

interface IBaseErrorManager<T> where T : IErrorListener {


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

    fun notifyErrorListeners(error: BaseUserBehaviorException)

}