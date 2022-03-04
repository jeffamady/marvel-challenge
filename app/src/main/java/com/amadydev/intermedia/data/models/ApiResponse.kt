package com.amadydev.intermedia.data.models

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    var code: Int,
    var data: T,
    @SerializedName("status") var message: String
)

