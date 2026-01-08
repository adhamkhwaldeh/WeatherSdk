package com.github.adhamkhwaldeh.commonsdk.listeners.errors

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException

interface IErrorListener {
    /**
     * On error
     *
     * @param error
     */
    fun onError(error: BaseUserBehaviorException)
}