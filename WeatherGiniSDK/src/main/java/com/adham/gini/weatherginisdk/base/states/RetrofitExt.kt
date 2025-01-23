package com.adham.gini.weatherginisdk.base.states

import retrofit2.Retrofit

/**
 * As bas state
 *
 * @param T
 * @param retrofit
 * @return
 */
fun <T> Result<T>.asBasState(retrofit: Retrofit): BaseState<T> {
    return if (isSuccess && getOrNull() != null) {
        BaseState.BaseStateLoadedSuccessfully(getOrNull()!!)
    } else {
        BaseState.getStateByThrowable(exceptionOrNull()!!, retrofit)
    }
}