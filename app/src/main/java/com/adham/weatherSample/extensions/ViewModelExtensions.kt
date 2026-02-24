package com.adham.weatherSample.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.adham.dvt.commonlibrary.R
import com.adham.dvt.commonlibrary.base.BaseRefactorViewModel
import com.adham.dvt.commonlibrary.base.states.BaseState
import kotlinx.coroutines.launch

fun BaseRefactorViewModel.showToast(
    @StringRes res: Int,
    duration: Int = Toast.LENGTH_LONG,
) {
    showToast(application.getString(res), duration)
}

fun BaseRefactorViewModel.showToast(
    content: String,
    duration: Int = Toast.LENGTH_LONG,
) {
    viewModelScope.launch {
        Toast.makeText(application, content, duration).show()
    }
}

fun BaseRefactorViewModel.defaultSideEffect(state: BaseState<*>) {
    val content =
        when (state) {
            is BaseState.InternalServerError<*> -> state.errorMessage
            is BaseState.InvalidData<*> -> state.responseMessage
            is BaseState.NoAuthorized<*> -> state.responseMessage
            is BaseState.NoInternetError<*> -> application.getString(R.string.noConnection)
            is BaseState.NotDataFound<*> -> application.getString(R.string.noDataFound)
            is BaseState.ValidationError<*> -> state.responseMessage
            else -> ""
//        is BaseState.Initial<*> ->
//        is BaseState.Loading<*> ->
//        is BaseState.LoadingDismiss<*> ->
//        is BaseState.BaseStateLoadedSuccessfully<*> ->
        }
    if (content.isNotEmpty()) {
        showToast(content)
    }
}
