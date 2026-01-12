package com.github.adhamkhwaldeh.commonlibrary.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * Base refactor view model
 *
 * @constructor
 *
 * @param androidApplication
 */
abstract class BaseRefactorViewModel(
    androidApplication: Application,
) : AndroidViewModel(androidApplication) { // , KoinComponent
}
