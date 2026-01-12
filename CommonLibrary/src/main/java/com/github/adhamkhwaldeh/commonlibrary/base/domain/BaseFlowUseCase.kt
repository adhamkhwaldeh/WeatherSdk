package com.github.adhamkhwaldeh.commonlibrary.base.domain

import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<out Type, in Params> where Type : Any? {
    abstract suspend operator fun invoke(params: Params): Flow<Type>
}
