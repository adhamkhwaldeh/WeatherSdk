package com.adham.weatherSdk.data.interfaces

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener
import java.lang.Exception

/**
 * On sdk status change listener
 *
 * @constructor Create empty On sdk status change listener
 */
interface OnSdkStatusChangeListener : ICallbackListener {


    /**
     * On sdk initialized
     *
     */
    fun onSdkInitialized()

}