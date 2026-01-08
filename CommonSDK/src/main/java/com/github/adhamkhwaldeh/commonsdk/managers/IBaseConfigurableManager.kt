package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.configs.IManagerConfigInterface

/**
 * IBaseConfigurableManager interface for managers that support configuration.
 */
interface IBaseConfigurableManager<T : IManagerConfigInterface> {

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): IBaseConfigurableManager<T>

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): IBaseConfigurableManager<T>

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): IBaseConfigurableManager<T>

    /**
     * Update config
     *
     * @param config
     * @return
     */

    fun updateConfig(config: T): IBaseConfigurableManager<T>

    fun updateDefaultConfig(config: IManagerConfigInterface): IBaseConfigurableManager<T>


    fun canOverride(): Boolean

}

