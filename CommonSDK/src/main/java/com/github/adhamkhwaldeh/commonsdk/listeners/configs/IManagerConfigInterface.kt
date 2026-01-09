package com.github.adhamkhwaldeh.commonsdk.listeners.configs

interface IManagerConfigInterface : IServiceStatusInterface, IBuildTypeInterface,
    ILogConfigInterface {

    fun updateDefaultConfig(changeOptions: (options: IManagerConfigInterface) -> IManagerConfigInterface){
        updateDefaultConfig(changeOptions(this))
    }

    fun updateDefaultConfig(configure: IManagerConfigInterface) {
        isLoggingEnabled = configure.isLoggingEnabled
        logLevel = configure.logLevel
        isEnabled = configure.isEnabled
        isDebugMode = configure.isDebugMode

//        overridable = configure.overridable
    }


}