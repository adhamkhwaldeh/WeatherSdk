package com.github.adhamkhwaldeh.commonlibrary.base.stateLayout

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.platform.LocalContext
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.defaultStates.InternalServerErrorCompose
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.defaultStates.NoInternetConnectionCompose
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.defaultStates.NotAuthorizedCompose
import com.github.adhamkhwaldeh.commonlibrary.base.stateLayout.defaultStates.Progress
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState

/**
 * States layout compose
 *
 * @param T
 * @param modifier
 * @param baseState
 * @param customContent
 * @param customAction
 * @param content
 */
@Composable
fun <T> StatesLayoutCompose(
    baseState: BaseState<T>,
    modifier: Modifier = Modifier,
    customContent: StatesLayoutCustomInterface? = null,
    customAction: StatesLayoutCustomActionInterface? = null,
    content: (
        @Composable
        @UiComposable (data: T) -> Unit
    )? = null,
) {
    Column(modifier = modifier) {
        when (baseState) {
            is BaseState.Initial -> {
                Box {}
            }

            is BaseState.Loading -> {
                Progress(value = true)
            }

            is BaseState.LoadingDismiss -> {
                Progress(value = false)
            }

            is BaseState.InternalServerError -> {
                InternalServerErrorState(baseState, customContent)
            }

            is BaseState.InvalidData -> {
                InvalidDataState(customContent)
            }

            is BaseState.NoAuthorized -> {
                NoAuthorizedState(customContent)
            }

            is BaseState.NoInternetError -> {
                NoInternetErrorState(customContent, customAction)
            }

            is BaseState.NotDataFound -> {
                NotDataFoundState(customContent)
            }

            is BaseState.ValidationError -> {
                ValidationErrorState(baseState, customContent)
            }

            is BaseState.BaseStateLoadedSuccessfully -> {
                SuccessState(baseState, content)
            }
        }
    }
}

@Composable
private fun InternalServerErrorState(
    baseState: BaseState.InternalServerError<*>,
    customContent: StatesLayoutCustomInterface?,
) {
    customContent?.internalServerError() ?: InternalServerErrorCompose(baseState.errorMessage)
}

@Composable
private fun InvalidDataState(customContent: StatesLayoutCustomInterface?) {
    customContent?.invalidData() ?: Box {}
}

@Composable
private fun NoAuthorizedState(customContent: StatesLayoutCustomInterface?) {
    customContent?.notAuthorized() ?: NotAuthorizedCompose()
}

@Composable
private fun NoInternetErrorState(
    customContent: StatesLayoutCustomInterface?,
    customAction: StatesLayoutCustomActionInterface?,
) {
    customContent?.noInternetError() ?: NoInternetConnectionCompose(retry = {
        customAction?.retry()
    })
}

@Composable
private fun NotDataFoundState(customContent: StatesLayoutCustomInterface?) {
    customContent?.noContent() ?: Box {}
}

@Composable
private fun ValidationErrorState(
    baseState: BaseState.ValidationError<*>,
    customContent: StatesLayoutCustomInterface?,
) {
    customContent?.validationError() ?: Box {
        val context = LocalContext.current
        LaunchedEffect(key1 = true) {
            val message =
                if (baseState.errorId != 0) {
                    context.getString(baseState.errorId)
                } else {
                    baseState.responseMessage
                }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun <T> SuccessState(
    baseState: BaseState.BaseStateLoadedSuccessfully<T>,
    content: (
        @Composable
        @UiComposable (data: T) -> Unit
    )?,
) {
    Box {
        content?.let { it(baseState.data) }
    }
}
