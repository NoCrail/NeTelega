package com.home.netelega.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun <T> apiCall(apiCall: () -> T): Result<T> =
    try {
        Result.Success(apiCall.invoke())
    } catch (t: Throwable) {
        Result.Failure(t)
    }