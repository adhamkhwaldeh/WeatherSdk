package com.adham.weatherSdk.exceptions

import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException

class ApiKeyNotValidException(
    message: String?,
    cause: Throwable? = null
) : BaseSDKException(message, cause)
