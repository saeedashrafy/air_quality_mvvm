package com.ashr.cleanMvvmAir.domain.model

data class DomainCityData(
    val domainPollutionData: DomainPollutionData,
    val domainWeatherData: DomainWeatherData
)

data class DomainPollutionData(
    val updatedTime: String,
    val airQualityIndexUs: Int,
    val aqiLevel: AqiLevel
)

data class DomainWeatherData(
    val updatedTime: String,
    val temperature: Int,
    val humidity: String,
    val pressure: Int
)