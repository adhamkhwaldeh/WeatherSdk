package com.adham.gini.weatherginisdk.data.interfaces

import java.lang.Exception

/**
 * On sdk status change listener
 *
 * @constructor Create empty On sdk status change listener
 */
interface OnSdkStatusChangeListener {
    /**
     * On finish
     *
     */
    fun onFinish()

    /**
     * On finish with error
     *
     * @param exception
     */
    fun onFinishWithError(exception: Exception?)
}