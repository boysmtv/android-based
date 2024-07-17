package com.kotlin.learn.core.common.base

import androidx.lifecycle.LiveData
import com.kotlin.learn.core.common.entity.Result
import com.kotlin.learn.core.utilities.Constant.ZERO
import com.kotlin.learn.core.utilities.httpError

fun <A> BaseFragment<*>.observeDataFlow(
    liveData: LiveData<out Result<A>>,
    specificGeneralError: List<String>? = null,
    isParallelCall: Boolean = false,
    onLoad: (() -> Unit)? = null,
    onError: ((Result<A>) -> Unit)? = null,
    onSuccess: ((A) -> Unit)? = null
) {
    liveData.observe(viewLifecycleOwner) { result ->
        result?.let {
            when (result.status) {
                Result.Status.LOADING -> onLoadForDataFlow(result, onLoad)
                Result.Status.SUCCESS -> onSuccessForDataFlow(result, onSuccess)
                Result.Status.ERROR -> onErrorForDataFlow(specificGeneralError, result, isParallelCall, onError)
            }
        }
    }
}

fun <A> BaseFragment<*>.onLoadForDataFlow(
    result: Result<A>,
    onLoad: (() -> Unit)? = null
) {
    result.getLoadingStateIfNotHandled()?.let {
        initCustomOnLoadState?.let { it() }

        onLoad?.let { it() }
    }
}

fun <A> BaseFragment<*>.onSuccessForDataFlow(
    result: Result<A>,
    onSuccess: ((A) -> Unit)? = null
) {
    retryCount = ZERO
    result.getSuccessStateIfNotHandled()?.let { data ->
        onSuccess?.let { it(data) }

        initCustomOnSuccessState?.let { it() }
    }
}

fun <A> BaseFragment<*>.onErrorForDataFlow(
    specificGeneralError: List<String>? = null,
    result: Result<A>,
    isParallelCall: Boolean,
    onError: ((Result<A>) -> Unit)? = null
) {
    result.getErrorStateIfNotHandled()?.let {
        initCustomOnErrorState?.let { it() }

        if (result.httpStatus in httpError && result.httpStatus !in specificGeneralError.orEmpty()
        ) {
            if (isParallelCall) onError?.invoke(result)
            errorHandler(
                result.code,
                result.title,
                result.message,
                result.httpStatus
            )
        } else {
            onError?.let { it(result) } ?: run {
                errorHandler(
                    result.code,
                    result.title,
                    result.message,
                    result.httpStatus
                )
            }
        }
    }
}