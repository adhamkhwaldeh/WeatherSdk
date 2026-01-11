package com.github.adhamkhwaldeh.commonsdk.listeners.errors

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException

interface ErrorListener {
    /**
     * On error
     *
     * @param error
     */
    fun onError(error: BaseSDKException)
}