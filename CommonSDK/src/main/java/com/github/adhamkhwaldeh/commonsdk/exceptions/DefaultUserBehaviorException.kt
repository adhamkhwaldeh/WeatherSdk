package com.github.adhamkhwaldeh.commonsdk.exceptions

class DefaultUserBehaviorException (
    override val message: String?,
    override val cause: Throwable? = null
) : BaseUserBehaviorException(message, cause)