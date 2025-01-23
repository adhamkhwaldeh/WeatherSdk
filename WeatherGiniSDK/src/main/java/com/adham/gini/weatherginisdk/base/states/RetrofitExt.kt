package com.adham.gini.weatherginisdk.base.states

import retrofit2.Retrofit


fun <T> Result<T>.asBasState(retrofit: Retrofit): BaseState<T> {
    return if (isSuccess && getOrNull() != null) {
        BaseState.BaseStateLoadedSuccessfully(getOrNull()!!)
    } else {
        BaseState.getStateByThrowable(exceptionOrNull()!!, retrofit)
    }
}

//suspend fun <T> Predicate<T>.requestSealedBlocking(retrofit: Retrofit): BaseState<T> {
//    var errorBody = ""
//    return try {
//        val response =  invoke()
//        when (response.isSuccessful) {
//            true -> BaseStateLoadedSuccessfully(response.body()) // Either.Right((response.body()))
//            false ->{
//                val gson = Gson()
//                val type = object : TypeToken<String>() {}.type
//                val errorResponse: String? =
//                    gson.fromJson(response.errorBody()!!.charStream(), type)
//                errorBody = errorResponse.toString()
//
//                BaseState.handleErrorBody(
//                    errorBody = response.errorBody(),
//                    retrofit = retrofit,
//                    code = response.code()
//                )
//            }
//
//            //Either.Left(getRemoteErrorMessage(response.errorBody()))
//        }
//    } catch (exception: Throwable) {
////        exception.printStackTrace()
////        Crashes.trackError(exception)
//        BaseState.getStateByThrowable(exception, errorMessage = errorBody)
////        Either.Left(getExceptionMessage(exception = exception))
//    }
//}

