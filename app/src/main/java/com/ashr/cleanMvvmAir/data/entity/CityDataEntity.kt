package com.ashr.cleanMvvmAir.data.entity

import com.ashr.cleanMvvmAir.domain.model.AqiLevel
import com.ashr.cleanMvvmAir.domain.model.DomainCityData
import com.ashr.cleanMvvmAir.domain.model.DomainPollutionData
import com.ashr.cleanMvvmAir.domain.model.DomainWeatherData
import java.text.SimpleDateFormat
import java.util.*


data class CityDataEntity(
    val pollution: PollutionEntity,
    val weather: WeatherEntity
)

fun CityDataEntity.toDomain(): DomainCityData {
    return DomainCityData(
        domainPollutionData = pollution.toDomain(),
        domainWeatherData = weather.toDomain()
    )
}

data class PollutionEntity(
    val timeStamp: String = "",
    val airQualityIndexUs: Int = 0
)

fun PollutionEntity.toDomain(): DomainPollutionData {
    return DomainPollutionData(
        convertFormatOfDate(
            timeStamp, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "HH:mm:ss"
        ),
        airQualityIndexUs = airQualityIndexUs,
        aqiLevel = getAqiLevel(airQualityIndexUs)
    )
}

fun getAqiLevel(aqi: Int): AqiLevel {
    when (aqi) {
        in 1..49 -> {
            return AqiLevel.Good
        }
        in 51..99 -> {
            return AqiLevel.Moderate
        }
        in 101..199 -> {
            return AqiLevel.UnhealthyForSensitiveGroups
        }
        in 201..299 -> {
            return AqiLevel.Unhealthy
        }
        in 301..399 -> {
            return AqiLevel.VeryUnhealthy
        }
        in 401..499 -> {
            return AqiLevel.Hazardous
        }
        else -> return AqiLevel.Unknown
    }
}

private fun convertFormatOfDate(
    dateString: String,
    currentFormat: String,
    desiredFormat: String
): String {
    val currentSdf = SimpleDateFormat(currentFormat, Locale.getDefault())
    val currentDate: Date? = currentSdf.parse(dateString)
    val desiredSdf = SimpleDateFormat(desiredFormat, Locale.getDefault())
    return desiredSdf.format(currentDate!!)
}

data class WeatherEntity(
    val timeStamp: String = "",
    val temperature: Int = 0,
    val humidity: String = "",
    val pressure: Int = 0
)

fun WeatherEntity.toDomain(): DomainWeatherData {
    return DomainWeatherData(
        updatedTime = timeStamp,
        temperature = temperature,
        humidity = humidity,
        pressure = pressure
    )
}
