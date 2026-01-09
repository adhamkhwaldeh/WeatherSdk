package com.github.adhamkhwaldeh.commonlibrary.base

import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
/**
 * Base sealed use case
 *
 * @param Type
 * @param Params
 * @constructor Create empty Base sealed use case
 */
abstract class BaseSealedUseCase<out Type, in Params> :
    BaseUseCase<BaseState<Type>, Params>() where Type : Any?