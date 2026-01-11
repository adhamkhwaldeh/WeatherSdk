package com.github.adhamkhwaldeh.commonsdk.listeners.configs

interface ManagerConfigInterface : ServiceStatusInterface, BuildTypeInterface,
    LogConfigInterface {

    fun updateDefaultConfig(changeOptions: (options: ManagerConfigInterface) -> ManagerConfigInterface){
        updateDefaultConfig(changeOptions(this))
    }

    fun updateDefaultConfig(configure: ManagerConfigInterface) {
        isLoggingEnabled = configure.isLoggingEnabled
        logLevel = configure.logLevel
        isEnabled = configure.isEnabled
        isDebugMode = configure.isDebugMode

//        overridable = configure.overridable
    }


}
