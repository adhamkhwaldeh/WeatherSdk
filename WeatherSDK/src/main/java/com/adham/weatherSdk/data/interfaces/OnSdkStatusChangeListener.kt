package com.adham.weatherSdk.data.interfaces

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener

/**
 * On sdk status change listener
 *
 * @constructor Create empty On sdk status change listener
 */
interface OnSdkStatusChangeListener : CallbackListener {


    /**
     * On sdk initialized
     *
     */
    fun onSdkInitialized()

}