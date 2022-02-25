package com.amadydev.intermedia.data.models

import retrofit2.Response

sealed class NetResult<out T> {

    data class Success<out T>(val data: T) : NetResult<T>()
    data class Error(val error: Response<*>, var code: Int? = null) : NetResult<Nothing>()
}