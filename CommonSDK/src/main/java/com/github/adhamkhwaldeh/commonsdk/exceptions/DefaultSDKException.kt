package com.github.adhamkhwaldeh.commonsdk.exceptions

class DefaultSDKException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseSDKException(message, cause)
