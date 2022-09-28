package com.ashr.cleanMvvmAir.remote.model

import com.ashr.cleanMvvmAir.data.entity.CityDataEntity
import com.ashr.cleanMvvmAir.data.entity.PollutionEntity
import com.ashr.cleanMvvmAir.data.entity.WeatherEntity
import com.google.gson.annotations.SerializedName

data class CityQualityResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    var data: NetworkCityQuality?
)

data class NetworkCityQuality(
    @SerializedName("current")
    val currentData: CurrentData
)

data class CurrentData(
    @SerializedName("pollution")
    val pollution: PollutionData,
    @SerializedName("weather")
    val weather: WeatherData
)

fun CurrentData.toEntity(): CityDataEntity {
    return CityDataEntity(pollution = pollution.toEntity(), weather = weather.toEntity())
}

data class PollutionData(
    @SerializedName("ts")
    val timeStamp: String,
    @SerializedName("aqius")
    val airQualityIndexUs: Int
)

fun PollutionData.toEntity(): PollutionEntity {
    return PollutionEntity(timeStamp = timeStamp, airQualityIndexUs = airQualityIndexUs)
}

data class WeatherData(
    @SerializedName("ts")
    val timeStamp: String,
    @SerializedName("tp")
    val temperature: Int,
    @SerializedName("hu")
    val humidity: String,
    @SerializedName("pr")
    val pressure: Int
)

fun WeatherData.toEntity(): WeatherEntity {
    return WeatherEntity(
        timeStamp = timeStamp,
        temperature = temperature,
        humidity = humidity,
        pressure = pressure
    )
}