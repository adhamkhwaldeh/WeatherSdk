package com.github.adhamkhwaldeh.commonsdk.sdks

import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

interface BaseConfigSDK< TConfig : BaseSDKOptions> {

    //#region SDK-level Configuration actions
    fun updateSDKConfig(changeOptions: (TConfig) -> TConfig)

    fun updateSDKConfig(newConfig: TConfig)
    //#endregion

    //#region SDK-level Logging actions
    fun updateLoggers(loggers: List<Logger>)
    //#endregion

}
