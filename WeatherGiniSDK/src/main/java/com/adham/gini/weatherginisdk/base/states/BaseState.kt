package com.adham.gini.weatherginisdk.base.states

import androidx.annotation.StringRes
//import com.tatweer.common.apiServices.interceptors.NetworkConnectionInterceptor
//import com.tatweer.common.data.enums.HttpResponseCodeEnum
//import com.tatweer.common.data.response.BaseResponse
//import com.tatweer.common.infrastructure.exception.Failure
import retrofit2.Retrofit
import java.net.UnknownHostException

/**
 * Base state
 *
 * @param T
 * @constructor Create empty Base state
 */

//TODO need to be reviewed
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
    data class InternalServerError<T>(var errorMessage: String) : BaseState<T>()

    /**
     * No authorized
     *
     * @param T
     * @property responseMessage
     * @constructor Create empty No authorized
     */
    data class NoAuthorized<T>(var responseMessage: String) : BaseState<T>()

    /**
     * Invalid data
     *
     * @param T
     * @property responseMessage
     * @constructor Create empty Invalid data
     */
    data class InvalidData<T>(var responseMessage: String) : BaseState<T>()

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
        @StringRes var errorId: Int = 0
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
     *///So to simplify the solution we have on case which is load the response
    data class BaseStateLoadedSuccessfully<T>(var data: T) : BaseState<T>()

    companion object {
        fun <T> getStateByThrowable(
            it: Throwable,
            retrofit: Retrofit? = null,
            errorMessage: String? = null
        ): BaseState<T> {
            return when (it) {
                is retrofit2.HttpException,
                is UnknownHostException -> {
//                    val response = it.response()
//                    val errorBody = response?.errorBody()
//                    handleErrorBody(errorBody, retrofit, response?.code())
                    NoInternetError()
                }

                else -> {
                    if (retrofit != null) {
                        InternalServerError(errorMessage ?: (it.message ?: ""))
                    } else {
                        InternalServerError(errorMessage ?: (it.message ?: ""))
                    }
                }
            }
        }

//        fun <T> handleErrorBody(
//            errorBody: ResponseBody?,
//            retrofit: Retrofit? = null, code: Int?,
//        ): BaseState<T> {
//            return try {
//                val converter: Converter<ResponseBody, BaseResponse<*>>? =
//                    retrofit?.responseBodyConverter(
//                        BaseResponse::class.java,
//                        arrayOf()
//                    )
//                val result: BaseResponse<*>? = errorBody?.let { converter?.convert(it) }
//                return when (code) {
//                    HttpResponseCodeEnum.UN_AUTHORIZED.code -> {
//                        NoAuthorized(result?.getErrorsCollections() ?: "")
//                    }
//
//                    HttpResponseCodeEnum.NO_DATA_FOUND.code -> {
//                        NotDataFound() //result?.getErrorsCollections()?: ""
//                    }
//
//                    HttpResponseCodeEnum.FORBIDDEN.code -> {
//                        NoAuthorized(result?.getErrorsCollections() ?: "")
//                    }
//
//                    HttpResponseCodeEnum.INTERNAL_SERVER_ERROR.code -> {
//                        InternalServerError(result?.getErrorsCollections() ?: "")
//                    }
//
//                    HttpResponseCodeEnum.BAD_REQUEST.code -> {
//                        InternalServerError(result?.getErrorsCollections() ?: "")
//                    }
//
//                    else -> NoInternetError()
//                }
//            } catch (ex: Exception) {
//                NoInternetError()
//            }
//
//        }
//
//        //TODO need to be changed
//        fun <T> handleByEither(failure: Failure?): BaseState<T> {
//            return NoInternetError<T>()
//        }
//
//        inline fun <reified T> getOtherThanFeature(baseState: BaseState<*>): BaseState<T>? {
//            return when (baseState) {
//                is FeaturedState -> null
//                is InternalServerError -> InternalServerError(baseState.errorMessage)
//                is Loading -> Loading()
//                is LoadingDismiss -> LoadingDismiss()
//                is NoAuthorized -> NoAuthorized(baseState.responseMessage)
//                is NoInternetError -> NoInternetError()
//                is NotDataFound -> NotDataFound()
//                is InvalidData -> InvalidData(baseState.responseMessage)
//                is ValidationError -> ValidationError(baseState.responseMessage, baseState.errorId)
//            }
//        }
//
//        inline fun <reified T> getOtherAndFeature(baseState: BaseState<*>, data: T): BaseState<T> {
//            return when (baseState) {
//                is FeaturedState -> BaseStateLoadedSuccessfully(data)
//                is InternalServerError -> InternalServerError(baseState.errorMessage)
//                is Loading -> Loading()
//                is LoadingDismiss -> LoadingDismiss()
//                is NoAuthorized -> NoAuthorized(baseState.responseMessage)
//                is NoInternetError -> NoInternetError()
//                is NotDataFound -> NotDataFound()
//                is InvalidData -> InvalidData(baseState.responseMessage)
//                is ValidationError -> ValidationError(baseState.responseMessage, baseState.errorId)
//            }
//        }

    }

}