package com.adham.gini.weatherginisdk.base

import com.adham.gini.weatherginisdk.base.states.BaseState
import kotlinx.coroutines.*

abstract class BaseSealedUseCase<out Type, in Params>(
    private val ioScope: CoroutineScope,
    val main: CoroutineDispatcher = Dispatchers.Main
) where Type : Any? {

    abstract suspend fun run(params: Params): BaseState<Type>?

    operator fun invoke(params: Params, onResult: (BaseState<Type>) -> Unit = {}) {
        ioScope.launch {
            val result = async { run(params) }
            withContext(main)
            {
                result.await()?.let { onResult(it) }
            }
        }
    }

    class None

}