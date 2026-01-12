package com.github.adhamkhwaldeh.commonsdk.managers

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener
import com.github.adhamkhwaldeh.commonsdk.listeners.configs.ManagerConfigInterface
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.Logger

/**
 * Base manager impl
 *
 * @param TCall
 * @param TError
 * @param TConfig
 * @property logger
 * @property callbackManager
 * @property errorManager
 * @property configurableManager
 * @constructor
 *
 * @param config
 */
abstract class BaseManagerImpl<TCall : CallbackListener, TError : ErrorListener, TConfig : ManagerConfigInterface>(
    config: TConfig,
    internal val logger: Logger,
    private val callbackManager: BaseCallbackManager<TCall> = BaseCallbackManagerImpl(),
    private val errorManager: BaseErrorManager<TError> = BaseErrorManagerImpl(),
    private val configurableManager: BaseConfigurableManager<TConfig> =
        BaseConfigurableManagerImpl(
            config,
        ),
) : BaseManager<TCall, TError, TConfig>,
    BaseCallbackManager<TCall> by callbackManager,
    BaseErrorManager<TError> by errorManager,
    BaseConfigurableManager<TConfig> by configurableManager
