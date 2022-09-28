package com.ashr.cleanMvvmAir.remote.model

import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    var data: List<NetworkCity>?
)

data class NetworkCity(
    @SerializedName("city")
    val name: String
)