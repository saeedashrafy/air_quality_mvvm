package com.ashr.cleanMvvmAir.remote.model

import com.google.gson.annotations.SerializedName

data class StateResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    var data: List<NetworkState>?
)

data class NetworkState(@SerializedName("state") val name: String)
