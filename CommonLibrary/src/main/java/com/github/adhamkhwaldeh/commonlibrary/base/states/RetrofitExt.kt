package com.github.adhamkhwaldeh.commonlibrary.base.states

/**
 * As bas state
 *
 * @param T
 * @return
 */
fun <T> Result<T>.asBasState(): BaseState<T> =
    if (isSuccess && getOrNull() != null) {
        val res = getOrNull()
        if (res != null) {
            BaseState.BaseStateLoadedSuccessfully(res)
        } else {
            BaseState.NotDataFound()
        }
    } else {
        BaseState.getStateByThrowable(exceptionOrNull() ?: Throwable(message = "Unexpected error"))
    }
