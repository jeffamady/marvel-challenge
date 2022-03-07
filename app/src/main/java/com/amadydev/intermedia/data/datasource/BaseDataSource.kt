package com.amadydev.intermedia.data

import com.amadydev.intermedia.R
import com.amadydev.intermedia.data.models.ApiResponse
import com.amadydev.intermedia.data.models.Data
import retrofit2.Response
import java.io.Serializable

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<ApiResponse<Data<T>>>): Resource<Data<T>> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.data?.let {
                    return Resource.success(it)
                }
            }
            return Resource.error(R.string.sorry_something_went_wrong)
        } catch (e: Exception) {
            return Resource.error(R.string.no_internet)
        }
    }
}

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val resId: Int = 0
) : Serializable {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data
            )
        }

        fun <T> error(resId: Int, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                resId
            )
        }
    }
}