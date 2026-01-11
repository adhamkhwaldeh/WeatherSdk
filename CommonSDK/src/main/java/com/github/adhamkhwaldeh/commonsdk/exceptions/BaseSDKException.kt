package com.github.adhamkhwaldeh.commonsdk.exceptions

abstract class BaseSDKException(
    override val message: String?,
    override val cause: Throwable? = null
) : Throwable(message, cause), SDKException {
    companion object {
        @JvmStatic
        fun fromException(throwable: Throwable): BaseSDKException { // Changed from Exception to Throwable
            return DefaultSDKException(
                message = throwable.message ?: "An unknown error occurred",
                cause = throwable  // Storing the original throwable is good practice
            )
        }
    }
}