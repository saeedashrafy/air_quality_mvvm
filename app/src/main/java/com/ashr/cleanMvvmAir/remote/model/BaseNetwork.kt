package com.ashr.cleanMvvmAir.remote.model

import com.google.gson.annotations.SerializedName

open class BaseNetwork<T> {
    @SerializedName("status")
    val status: String? = null

    @SerializedName("data")
    open var data: T? = null

}
