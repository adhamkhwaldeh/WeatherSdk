package com.github.adhamkhwaldeh.commonlibrary.base.states

import androidx.annotation.StringRes
import com.github.adhamkhwaldeh.commonlibrary.base.helpers.CommonConstantHelper
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

/**
 * Base state
 *
 * @param T
 * @constructor Create empty Base state
 */

sealed class BaseState<out T> {
    /**
     * Initial
     *
     * @param T
     * @constructor Create empty Initial
     */
    class Initial<T> : BaseState<T>()

    /**
     * Loading
     *
     * @param T
     * @constructor Create empty Loading
     */
    class Loading<T> : BaseState<T>()

    /**
     * Loading dismiss
     *
     * @param T
     * @constructor Create empty Loading dismiss
     */
    class LoadingDismiss<T> : BaseState<T>()

    /**
     * No internet error
     *
     * @param T
     * @constructor Create empty No internet error
     */
    class NoInternetError<T> : BaseState<T>()

    /**
     * Not data found
     *
     * @param T
     * @constructor Create empty Not data found
     */
    class NotDataFound<T> : BaseState<T>()

    /**
     * Internal server error
     *
     * @param T
     * @property errorMessage
     * @constructor Create empty Internal server error
     */
    data class InternalServerError<T>(
        var errorMessage: String,
    ) : BaseState<T>()

    /**
     * No authorized
     *
     * @param T
     * @property responseMessage
     * @constructor Create empty No authorized
     */
    data class NoAuthorized<T>(
        var responseMessage: String,
    ) : BaseState<T>()

    /**
     * Invalid data
     *
     * @param T
     * @property responseMessage
     * @constructor Create empty Invalid data
     */
    data class InvalidData<T>(
        var responseMessage: String,
    ) : BaseState<T>()

    /**
     * Validation error
     *
     * @param T
     * @property responseMessage
     * @property errorId
     * @constructor Create empty Validation error
     */
    data class ValidationError<T>(
        var responseMessage: String = "",
        @param:StringRes var errorId: Int = 0,
    ) : BaseState<T>()

    // I'm using FeaturedState to manage multiple cases e.g (load item by Id, add or update item, load list of items)
    // Will comment it and include BaseStateLoadedSuccessfully directly
//    abstract class FeaturedState<T> : BaseState<T>()
//    data class BaseStateLoadedSuccessfully<T>(var data: T?) : BaseState.FeaturedState<T>()

    /**
     * Base state loaded successfully
     *
     * @param T
     * @property data
     * @constructor Create empty Base state loaded successfully
     **/
    data class BaseStateLoadedSuccessfully<T>(
        var data: T,
    ) : BaseState<T>()

    companion object {
        fun <T> getStateByThrowable(
            it: Throwable,
            errorMessage: String? = null,
        ): BaseState<T> =
            when (it) {
                is HttpException -> handleHttpException(it, errorMessage)
                is UnknownHostException -> NoInternetError()
                is IOException -> NoInternetError()
                is IllegalArgumentException -> ValidationError()
                else -> InternalServerError(errorMessage ?: (it.message ?: ""))
            }

        private fun <T> handleHttpException(
            it: HttpException,
            errorMessage: String?,
        ): BaseState<T> {
            return when (it.code()) {
                CommonConstantHelper.NOT_AUTHORIZED_CODE -> NoAuthorized(it.message ?: "")
                CommonConstantHelper.INTERNAL_ERROR_400,
                CommonConstantHelper.INTERNAL_ERROR_500,
                -> InternalServerError(errorMessage ?: (it.message ?: ""))

                else -> NoInternetError()
            }
        }
    }
}
