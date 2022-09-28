package com.ashr.cleanMvvmAir.remote.model

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    var data: List<NetworkCountry>?
)

data class NetworkCountry(
    @SerializedName("country")
    val name: String
)