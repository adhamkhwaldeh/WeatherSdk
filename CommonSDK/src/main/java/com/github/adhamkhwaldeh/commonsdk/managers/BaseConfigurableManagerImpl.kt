package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.configs.ManagerConfigInterface

internal class BaseConfigurableManagerImpl<T : ManagerConfigInterface>(
    internal var config: T
) : BaseConfigurableManager<T> {

    override fun setEnabled(enabled: Boolean): BaseConfigurableManager<T> {
        this.config.isEnabled = enabled
        return this
    }

    override fun setDebugMode(debugMode: Boolean): BaseConfigurableManager<T> {
        this.config.isDebugMode = debugMode
        return this
    }

    override fun setLoggingEnabled(loggingEnabled: Boolean): BaseConfigurableManager<T> {
        config.isLoggingEnabled = loggingEnabled
        return this
    }

    override fun updateConfig(changeOptions: (T) -> T): BaseConfigurableManager<T> {
        updateConfig(changeOptions(config))
        return this
    }

    override fun updateConfig(config: T): BaseConfigurableManager<T> {
        this.config = config
        return this
    }

    override fun updateDefaultConfig(
        changeOptions: (options: ManagerConfigInterface) -> ManagerConfigInterface
    ): BaseConfigurableManager<T> {
        this.config.updateDefaultConfig(changeOptions)
        return this
    }

    override fun updateDefaultConfig(config: ManagerConfigInterface): BaseConfigurableManager<T> {
        this.config.updateDefaultConfig(config)
        return this
    }

    override fun canOverride(): Boolean {
        return this.config.overridable
    }
}
