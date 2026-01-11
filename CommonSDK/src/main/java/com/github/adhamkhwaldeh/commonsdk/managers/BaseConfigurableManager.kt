package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.configs.ManagerConfigInterface

/**
 * IBaseConfigurableManager interface for managers that support configuration.
 */
interface BaseConfigurableManager<T : ManagerConfigInterface> {

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): BaseConfigurableManager<T>

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): BaseConfigurableManager<T>

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): BaseConfigurableManager<T>

    /**
     * Update config
     *
     * @param config
     * @return
     */

    fun updateConfig(changeOptions: (options: T) -> T): BaseConfigurableManager<T>
    fun updateConfig(config: T): BaseConfigurableManager<T>

    fun updateDefaultConfig(
        changeOptions:
            (options: ManagerConfigInterface) -> ManagerConfigInterface
    ): BaseConfigurableManager<T>

    fun updateDefaultConfig(config: ManagerConfigInterface): BaseConfigurableManager<T>

    fun canOverride(): Boolean

}
