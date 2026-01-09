package com.github.adhamkhwaldeh.commonsdk.listeners.errors

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException

interface IErrorListener {
    /**
     * On error
     *
     * @param error
     */
    fun onError(error: BaseSDKException)
}